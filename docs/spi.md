为了使qtools具有良好的扩展性，qtools采用了SPI允许使用者对其实现类进行扩展

它具体的封装是在ToolsLoader下，首先利用Java自带的SPI机制，去加载qtools自身的Loading实现类:

    //LoadingStrategies实现类的存放数组
    private static volatile LoadingStrategy[] strategies = loadLoadingStrategies();

    //通过ServiceLoader的Spliterator遍历获取实现类
    private static LoadingStrategy[] loadLoadingStrategies() {
        return stream(load(LoadingStrategy.class).spliterator(), false)
                .sorted()
                .toArray(LoadingStrategy[]::new);
    }

这里的load(LoadingStrategy.class)实际就是ServiceLoader.load(LoadingStrategy.class)

    public static <S> ServiceLoader<S> load(Class<S> service) {
        //获取当前线程绑定的ClassLoader
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return ServiceLoader.load(service, cl);
    }

    public static <S> ServiceLoader<S> load(Class<S> service,
                                            ClassLoader loader)
    {
        return new ServiceLoader<>(service, loader);
    }

    private ServiceLoader(Class<S> svc, ClassLoader cl) {
        service = Objects.requireNonNull(svc, "Service interface cannot be null");
        //如果当前线程没有绑定的ClassLoader，就用systemClassLoader
        loader = (cl == null) ? ClassLoader.getSystemClassLoader() : cl;
        acc = (System.getSecurityManager() != null) ? AccessController.getContext() : null;
        reload();
    }

    public void reload() {
        //把已经加载过的实现类清除缓存
        providers.clear();
        //重新创建一个新的LazyIterator，之后返回ServiceLoader实例
        //当没有实现类缓存时，则使用这个LazyIterator加载实现类
        lookupIterator = new LazyIterator(service, loader);
    }

很多网上的代码，在创建完ServiceLoader实例后，马上就使用ServiceLoader.iterator()，然后hasNext和next来获取具体的实现类，像这样：

    ServiceLoader<LoadingStrategy> strategies = ServiceLoader.load(LoadingStrategy.class);
    Iterator<LoadingStrategy> iterator = strategies.iterator();
    while (iterator.hasNext()) {
        System.out.println(iterator.next());
    }

但从Java8开始，就已经提供了Spliterator来支持数据的并行处理

所以qtools在获取了ServiceLoader的实例后，马上调用load(LoadingStrategy.class).spliterator()方法，通过Spliterator来获取LoadingStrategy的具体实现类

    stream(load(LoadingStrategy.class).spliterator(), false)
        .sorted()
        .toArray(LoadingStrategy[]::new)

在执行.toArray(LoadingStrategy[]::new)方法时，将会调用ServiceLoader的iterator().hasNext()，来遍历获取ServiceLoader的LoadingStrategy实现类

    public Iterator<S> iterator() {
        return new Iterator<S>() {
            //只要不是第一次执行，基本都会有实现类的缓存，那么就不需要重复读取META-INF文件，直接读取缓存的实现类即可
            Iterator<Map.Entry<String,S>> knownProviders
                = providers.entrySet().iterator();

            public boolean hasNext() {
                //有缓存就不读META-INF/service
                if (knownProviders.hasNext())
                    return true;
                //真正通过service配置文件加载SPI的地方
                return lookupIterator.hasNext();
            }
            public S next() {
                if (knownProviders.hasNext())
                    return knownProviders.next().getValue();
                return lookupIterator.next();
            }
        };
    }

系统第一次加载LoadingStrategy类的时候，肯定是没有缓存的，所以需要调用之前谈到LazyIterator下的hasNext()方法来获取具体的实现类类名。

    public boolean hasNext() {
        if (acc == null) {
            return hasNextService();
        } else {
            PrivilegedAction<Boolean> action = new PrivilegedAction<Boolean>() {
                public Boolean run() { return hasNextService(); }
            };
            return AccessController.doPrivileged(action, acc);
        }
    }

    private boolean hasNextService() {
        if (nextName != null) {
            return true;
        }
        if (configs == null) {
            try {
                //获取META-INF/service下以类名开头的文件全路径
                String fullName = PREFIX + service.getName();
                if (loader == null)
                    configs = ClassLoader.getSystemResources(fullName);
                else
                    configs = loader.getResources(fullName);
            } catch (IOException x) {
                fail(service, "Error locating configuration files", x);
            }
        }
        //META-INF/service/LoadingStrategy的实现可能为复数个，所以用一个名为pending的iterator存放具体类名
        while ((pending == null) || !pending.hasNext()) {
            if (!configs.hasMoreElements()) {
                return false;
            }
            //按行解析META-INF/service/LoadingStrategy文件内容，并确保做为内容的类名是合法的java类名
            pending = parse(service, configs.nextElement());
        }
        nextName = pending.next();
        return true;
    }

获取实现类的类名之后，通过LazyIterator下的nextService()来实例化这些类

    private S nextService() {
        if (!hasNextService())
            throw new NoSuchElementException();
        String cn = nextName;
        nextName = null;
        Class<?> c = null;
        try {
            c = Class.forName(cn, false, loader);
        } catch (ClassNotFoundException x) {
            fail(service,
                    "Provider " + cn + " not found");
        }
        //要求找到的实现类必须是接口类的子类
        if (!service.isAssignableFrom(c)) {
            fail(service,
                    "Provider " + cn  + " not a subtype");
        }
        try {
            //实例化实现类，并类型转换为接口类
            S p = service.cast(c.newInstance());
            //将具体的实现类放入缓存中，下次调用不用重复解析META-INF下面的service文件内容
            providers.put(cn, p);
            return p;
        } catch (Throwable x) {
            fail(service,
                    "Provider " + cn + " could not be instantiated",
                    x);
        }
        throw new Error();          // This cannot happen
    }

到这里为止，qtools利用Java SPI加载的扩展已经基本结束，其他的类扩展，都属于LoadingStrategy策略下的加载 why?

因为Java SPI的实现逻辑存在一个很大的不足：无法按需加载实现类，而是在查找扩展实现类的时候遍历SPI的配置文件并且将实现类全部实例化，假设一个实现类初始化过程比较消耗资源且耗时，但是你的代码里面又用不上它，这就产生了资源的浪费

LoaddingStrategy策略下的加载扩展实现策略，也很简单，就是定义了META-INF/qtools/目录，放置要扩展的接口类文件，其内容为"扩展名 = 具体实现类" 在需要获取实现类时，通过指定扩展名即可。

    ToolsLoader<AsyncTool> toolsLoader = ToolsLoader.getToolsLoader(AsyncTool.class);
    AsyncTool asyncTool = toolsLoader.getTool("default");

    







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
        //遍历读取实现类，使用的就是这个LazyIterator
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
                //真正执行加载SPI的地方
                return lookupIterator.hasNext();
            }
            public S next() {
                if (knownProviders.hasNext())
                    return knownProviders.next().getValue();
                return lookupIterator.next();
            }
        };
    }

系统第一次加载LoadingStrategy类的时候，肯定是没有缓存的，所以需要调用之前谈到LazyIterator下的hasNext()方法

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
                String fullName = PREFIX + service.getName();
                if (loader == null)
                    configs = ClassLoader.getSystemResources(fullName);
                else
                    configs = loader.getResources(fullName);
            } catch (IOException x) {
                fail(service, "Error locating configuration files", x);
            }
        }
        while ((pending == null) || !pending.hasNext()) {
            if (!configs.hasMoreElements()) {
                return false;
            }
            pending = parse(service, configs.nextElement());
        }
        nextName = pending.next();
        return true;
    }





SPI(Service Provider Interface)它约定在 Classpath 下的 META-INF/services/ 目录里创建一个以服务接口命名的文件，然后文件里面记录的是此 jar 包提供的具体实现类的全限定名。

这样当我们引用了某个 jar 包的时候就可以去找这个 jar 包的 META-INF/services/ 目录，再根据接口名找到文件，然后读取文件里面的内容去进行实现类的加载与实例化。

Java SPI的源代码实现：

ServiceLoader.load(${className})是Java SPI的入口

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

    public S next() {
        if (acc == null) {
            return nextService();
        } else {
            PrivilegedAction<S> action = new PrivilegedAction<S>() {
                public S run() { return nextService(); }
            };
            return AccessController.doPrivileged(action, acc);
        }
    }


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
        if (!service.isAssignableFrom(c)) {
            fail(service,
                 "Provider " + cn  + " not a subtype");
        }
        try {
            S p = service.cast(c.newInstance());
            providers.put(cn, p);
            return p;
        } catch (Throwable x) {
            fail(service,
                 "Provider " + cn + " could not be instantiated",
                 x);
        }
        throw new Error();          // This cannot happen
    }

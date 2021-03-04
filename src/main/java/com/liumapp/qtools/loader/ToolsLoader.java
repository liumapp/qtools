package com.liumapp.qtools.loader;

import com.liumapp.qtools.classloader.ClassUtils;
import com.liumapp.qtools.container.Holder;
import com.liumapp.qtools.core.annotations.ExtensionFactory;
import com.liumapp.qtools.core.annotations.SPI;
import com.liumapp.qtools.core.utils.ArrayUtils;
import com.liumapp.qtools.core.utils.CollectionUtils;
import com.liumapp.qtools.core.utils.StringUtils;
import com.liumapp.qtools.loading.LoadingStrategy;
import com.liumapp.qtools.sort.Prioritized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

import static java.util.stream.StreamSupport.stream;
import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static java.util.ServiceLoader.load;
/**
 * This class are at preset designed to be singleton or static .
 * So the instances returned from them are of process or classloader scope.
 * <p>
 *     Load tool extensions
 * </p>
 *
 * @file ToolsLoader.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/1 20:45
 */
public class ToolsLoader<T> {

    private final Class<?> type;

    private static final Logger logger = LoggerFactory.getLogger(ToolsLoader.class);

    private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

    /**
     * container for tools loader instance
     */
    private static final ConcurrentMap<Class<?>, ToolsLoader<?>> TOOLS_LOADERS = new ConcurrentHashMap<>(64);

    /**
     * container for tools instance
     */
    private static final ConcurrentMap<Class<?>, Object> TOOLS_INSTANCES = new ConcurrentHashMap<>(64);

    /**
     * container for extension name and class
     * <p>
     *     key = extension name
     *     value = class
     * </p>
     */
    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    private Map<String, IllegalStateException> exceptions = new ConcurrentHashMap<>();

    /**
     * container for class and extension name
     * <p>
     *     key = class detail
     *     value = extension name
     * </p>
     */
    private final ConcurrentMap<Class<?>, String> cachedNames = new ConcurrentHashMap<>();

    private String cachedDefaultName;

//    private final ExtensionFactory objectFactory;

    /**
     * <p> spi loading strategy </p>
     * <p> now support META-INF/services and META-INF/qtools </p>
     * @See LoadingStrategy
     */
    private static volatile LoadingStrategy[] strategies = loadLoadingStrategies();

    /**
     * The container for tool extension name.
     * <p>
     *     key = tool extension name
     *     value = the Holder {@link Holder} for detail impl class
     * </p>
     */
    private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();

    private ToolsLoader(Class<?> type) {
        this.type = type;
    }

    public static <T> ToolsLoader<T> getToolsLoader(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Tools type == null");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Tools type (" + type + ") is not an interface");
        }
        if (!type.isAnnotationPresent(SPI.class)) {
            throw new IllegalArgumentException("Tools type (" + type + ") is not an extension, because it is not anootatino with @" + SPI.class.getSimpleName() + "!");
        }
        ToolsLoader<T> loader = (ToolsLoader<T>) TOOLS_LOADERS.get(type);
        if (loader == null) {
            TOOLS_LOADERS.putIfAbsent(type, new ToolsLoader<T>(type));
            loader = (ToolsLoader<T>) TOOLS_LOADERS.get(type);
        }
        return loader;
    }

    public T getTool(String name) {
        if ("true".equals(name) || StringUtils.isEmpty(name)) {
            return getDefaultTool();
        }

        final Holder<Object> holder = getOrCreateHolder(name);
        Object instance = holder.get();
        if (instance == null) {
            synchronized (holder) {
                instance = holder.get();
                if (instance == null) {
                    instance = createTool(name);
                    holder.set(instance);
                }
            }
        }
        return (T) instance;
    }

    /**
     * create the detail tool class for extension name
     * @param name the extension name
     * @return <E> non-null
     */
    private T createTool (String name) {
        Class<?> clazz = getToolClasses().get(name);
        if (clazz == null) {
            throw findException(name);
        }

        try {
            T instance = (T) TOOLS_INSTANCES.get(clazz);
            if (instance == null) {
                TOOLS_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
                instance = (T) TOOLS_INSTANCES.get(clazz);
            }
            return instance;
        } catch (Throwable t) {
            throw new IllegalStateException("Extension instance (name: " + name + ", class: " +
                    type + ") couldn't be instantiated: " + t.getMessage(), t);
        }
    }

    public Set<T> getSupportedExtensionInstances() {
        List<T> instances = new LinkedList<>();
        Set<String> supportedExtensions = getSupportedExtensions();
        if (CollectionUtils.isNotEmpty(supportedExtensions)) {
            for (String name : supportedExtensions) {
                instances.add(getTool(name));
            }
        }
        // sort the Prioritized instances
        sort(instances, Prioritized.COMPARATOR);
        return new LinkedHashSet<>(instances);
    }

    public Set<String> getSupportedExtensions() {
        Map<String, Class<?>> clazzes = getToolClasses();
        return Collections.unmodifiableSet(new TreeSet<>(clazzes.keySet()));
    }

//    private T injectExtension(T instance) {
//
//        if (objectFactory == null) {
//            return instance;
//        }
//
//        try {
//            for (Method method : instance.getClass().getMethods()) {
//                if (!isSetter(method)) {
//                    continue;
//                }
//                /**
//                 * Check {@link DisableInject} to see if we need auto injection for this property
//                 */
//                if (method.getAnnotation(DisableInject.class) != null) {
//                    continue;
//                }
//                Class<?> pt = method.getParameterTypes()[0];
//                if (ReflectUtils.isPrimitives(pt)) {
//                    continue;
//                }
//
//                try {
//                    String property = getSetterProperty(method);
//                    Object object = objectFactory.getExtension(pt, property);
//                    if (object != null) {
//                        method.invoke(instance, object);
//                    }
//                } catch (Exception e) {
//                    logger.error("Failed to inject via method " + method.getName()
//                            + " of interface " + type.getName() + ": " + e.getMessage(), e);
//                }
//
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//        return instance;
//    }

    private Holder<Object> getOrCreateHolder(String name) {
        Holder<Object> holder = cachedInstances.get(name);
        if (holder == null) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }
        return holder;
    }

    public T getDefaultTool() {
        getToolClasses();
        if (StringUtils.isBlank(cachedDefaultName) || "true".equals(cachedDefaultName)) {
            return null;
        }
        return getTool(cachedDefaultName);
    }

    public Class<?> getToolClass (String name) {
        if (name == null) {
            throw new IllegalArgumentException("tool name == null");
        }
        return getToolClasses().get(name);
    }

    private Map<String, Class<?>> getToolClasses () {
        Map<String, Class<?>> classes = cachedClasses.get();
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.get();
                if (classes == null) {
                    classes = loadToolClasses();
                    cachedClasses.set(classes);
                }
            }
        }
        return classes;
    }

    /**
     * load interface class by META-INF config
     * @return <code>Map<String, Class<?>></code>
     */
    private Map<String, Class<?>> loadToolClasses () {
        cachedDefaultToolName();
        Map<String, Class<?>> toolClasses = new HashMap<>();

        for (LoadingStrategy strategy : strategies) {
            loadDirectory(toolClasses, strategy.directory(), type.getName(), strategy.preferExtensionClassLoader(), strategy.overridden(), strategy.excludedPackages());
        }
        return toolClasses;
    }

    private void cachedDefaultToolName () {
        final SPI defaultAnnotation = type.getAnnotation(SPI.class);
        if (defaultAnnotation == null) {
            return ;
        }

        String value = defaultAnnotation.value();
        if ((value = value.trim()).length() > 0) {
            String[] names = NAME_SEPARATOR.split(value);
            if (names.length > 1) {
                throw new IllegalStateException("more than 1 default tools name on tool " + type.getName() + ": " + Arrays.toString(names));
            }
            if (names.length == 1) {
                cachedDefaultName = names[0];
            }
        }
    }
    /**
     * Load all {@link Prioritized} and {@link LoadingStrategy}
     * @return non-null
     * @since 2.0.5
     */
    private static LoadingStrategy[] loadLoadingStrategies() {
        return stream(load(LoadingStrategy.class).spliterator(), false)
                .sorted()
                .toArray(LoadingStrategy[]::new);
    }

    /**
     * read META-INF/qtools and META-INF/services
     * @param extensionClasses class impl
     * @param dir directory
     * @param type class type
     * @param extensionLoaderClassLoaderFirst extensionLoaderClassLoaderFirst
     * @param overridden overridden
     * @param excludedPackages excludedPackages
     */
    private void loadDirectory(Map<String, Class<?>> extensionClasses, String dir, String type,
                               boolean extensionLoaderClassLoaderFirst, boolean overridden, String... excludedPackages) {
        String fileName = dir + type;
        try {
            Enumeration<URL> urls = null;
            ClassLoader classLoader = findClassLoader();

            // try to load from ExtensionLoader's ClassLoader first
            if (extensionLoaderClassLoaderFirst) {
                ClassLoader toolsLoaderClassLoader = ToolsLoader.class.getClassLoader();
                if (ClassLoader.getSystemClassLoader() != toolsLoaderClassLoader) {
                    urls = toolsLoaderClassLoader.getResources(fileName);
                }
            }

            if (urls == null || !urls.hasMoreElements()) {
                if (classLoader != null) {
                    urls = classLoader.getResources(fileName);
                } else {
                    urls = ClassLoader.getSystemResources(fileName);
                }
            }

            if (urls != null) {
                while (urls.hasMoreElements()) {
                    java.net.URL resourceURL = urls.nextElement();
                    loadResource(extensionClasses, classLoader, resourceURL, overridden, excludedPackages);
                }
            }
        } catch (Throwable t) {
            logger.error("Exception occurred when loading extension class (interface: " +
                    type + ", description file: " + fileName + ").", t);
        }
    }

    private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader,
                              java.net.URL resourceURL, boolean overridden, String... excludedPackages) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    final int ci = line.indexOf('#');
                    if (ci >= 0) {
                        line = line.substring(0, ci);
                    }
                    line = line.trim();
                    if (line.length() > 0) {
                        try {
                            String name = null;
                            int i = line.indexOf('=');
                            if (i > 0) {
                                name = line.substring(0, i).trim();
                                line = line.substring(i + 1).trim();
                            }
                            if (line.length() > 0 && !isExcluded(line, excludedPackages)) {
                                loadClass(extensionClasses, resourceURL, Class.forName(line, true, classLoader), name, overridden);
                            }
                        } catch (Throwable t) {
                            IllegalStateException e = new IllegalStateException("Failed to load extension class (interface: " + type + ", class line: " + line + ") in " + resourceURL + ", cause: " + t.getMessage(), t);
                            exceptions.put(line, e);
                        }
                    }
                }
            }
        } catch (Throwable t) {
            logger.error("Exception occurred when loading extension class (interface: " +
                    type + ", class file: " + resourceURL + ") in " + resourceURL, t);
        }
    }

    private boolean isExcluded(String className, String... excludedPackages) {
        if (excludedPackages != null) {
            for (String excludePackage : excludedPackages) {
                if (className.startsWith(excludePackage + ".")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void loadClass(Map<String, Class<?>> extensionClasses, java.net.URL resourceURL, Class<?> clazz, String name,
                           boolean overridden) throws NoSuchMethodException {
        if (!type.isAssignableFrom(clazz)) {
            throw new IllegalStateException("Error occurred when loading extension class (interface: " +
                    type + ", class line: " + clazz.getName() + "), class " +
                    clazz.getName() + " is not subtype of interface.");
        }
        clazz.getConstructor();
        if (StringUtils.isEmpty(name)) {
            name = findAnnotationName(clazz);
            if (name.length() == 0) {
                throw new IllegalStateException("No such extension name for the class " + clazz.getName() + " in the config " + resourceURL);
            }
        }

        String[] names = NAME_SEPARATOR.split(name);
        if (ArrayUtils.isNotEmpty(names)) {
            for (String n : names) {
                cacheName(clazz, n);
                saveInExtensionClass(extensionClasses, clazz, n, overridden);
            }
        }
    }

    private static ClassLoader findClassLoader() {
        return ClassUtils.getClassLoader(ToolsLoader.class);
    }

    private String findAnnotationName(Class<?> clazz) {
        SPI spi = clazz.getAnnotation(SPI.class);
        if (spi != null) {
            return spi.value();
        }

        String name = clazz.getSimpleName();
        if (name.endsWith(type.getSimpleName())) {
            name = name.substring(0, name.length() - type.getSimpleName().length());
        }
        return name.toLowerCase();
    }

    private void saveInExtensionClass(Map<String, Class<?>> extensionClasses, Class<?> clazz, String name, boolean overridden) {
        Class<?> c = extensionClasses.get(name);
        if (c == null || overridden) {
            extensionClasses.put(name, clazz);
        } else if (c != clazz) {
            String duplicateMsg = "Duplicate extension " + type.getName() + " name " + name + " on " + c.getName() + " and " + clazz.getName();
            logger.error(duplicateMsg);
            throw new IllegalStateException(duplicateMsg);
        }
    }

    private void cacheName(Class<?> clazz, String name) {
        if (!cachedNames.containsKey(clazz)) {
            cachedNames.put(clazz, name);
        }
    }

//    @SuppressWarnings("unchecked")
//    public T getAdaptiveExtension() {
//        Object instance = cachedAdaptiveInstance.get();
//        if (instance == null) {
//            if (createAdaptiveInstanceError != null) {
//                throw new IllegalStateException("Failed to create adaptive instance: " +
//                        createAdaptiveInstanceError.toString(),
//                        createAdaptiveInstanceError);
//            }
//
//            synchronized (cachedAdaptiveInstance) {
//                instance = cachedAdaptiveInstance.get();
//                if (instance == null) {
//                    try {
//                        instance = createAdaptiveExtension();
//                        cachedAdaptiveInstance.set(instance);
//                    } catch (Throwable t) {
//                        createAdaptiveInstanceError = t;
//                        throw new IllegalStateException("Failed to create adaptive instance: " + t.toString(), t);
//                    }
//                }
//            }
//        }
//
//        return (T) instance;
//    }

    private IllegalStateException findException(String name) {
        for (Map.Entry<String, IllegalStateException> entry : exceptions.entrySet()) {
            if (entry.getKey().toLowerCase().contains(name.toLowerCase())) {
                return entry.getValue();
            }
        }
        StringBuilder buf = new StringBuilder("No such extension " + type.getName() + " by name " + name);


        int i = 1;
        for (Map.Entry<String, IllegalStateException> entry : exceptions.entrySet()) {
            if (i == 1) {
                buf.append(", possible causes: ");
            }

            buf.append("\r\n(");
            buf.append(i++);
            buf.append(") ");
            buf.append(entry.getKey());
            buf.append(":\r\n");
            buf.append(StringUtils.toString(entry.getValue()));
        }
        return new IllegalStateException(buf.toString());
    }

}

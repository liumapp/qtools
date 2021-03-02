package com.liumapp.qtools.loader;

import com.liumapp.qtools.classloader.ClassUtils;
import com.liumapp.qtools.container.Holder;
import com.liumapp.qtools.core.annotations.SPI;
import com.liumapp.qtools.core.utils.ArrayUtils;
import com.liumapp.qtools.core.utils.StringUtils;
import com.liumapp.qtools.loading.LoadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

import static java.util.stream.StreamSupport.stream;
import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static java.util.ServiceLoader.load;
/**
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

    private static final ConcurrentMap<Class<?>, ToolsLoader<?>> TOOLS_LOADERS = new ConcurrentHashMap<>(64);

    private static final ConcurrentMap<Class<?>, Object> TOOLS_INSTANCES = new ConcurrentHashMap<>(64);

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    private Map<String, IllegalStateException> exceptions = new ConcurrentHashMap<>();

    private String cachedDefaultName;

    private static volatile LoadingStrategy[] strategies = loadLoadingStrategies();

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
        if (type.isAnnotationPresent(SPI.class)) {
            throw new IllegalArgumentException("Tools type (" + type + ") is not an extension, because it is not anootatino with @" + SPI.class.getSimpleName() + "!");
        }
        ToolsLoader<T> loader = (ToolsLoader<T>) TOOLS_LOADERS.get(type);
        if (loader == null) {
            TOOLS_LOADERS.putIfAbsent(type, new ToolsLoader<T>(type));
            loader = (ToolsLoader<T>) TOOLS_LOADERS.get(type);
        }
        return loader;
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
//                    classes =
                }
            }
        }
        return null;
    }

    private Map<String, Class<?>> loadToolClasses () {
        cachedDefaultToolName();
        Map<String, Class<?>> toolClasses = new HashMap<>();

        for (LoadingStrategy strategy : strategies) {

        }
        return null;
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

//    public T getTool(Class<?> clazz) {
//
//    }

    private static LoadingStrategy[] loadLoadingStrategies() {
        return stream(load(LoadingStrategy.class).spliterator(), false)
                .sorted()
                .toArray(LoadingStrategy[]::new);
    }

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
                    type + ", class line: " + clazz.getName() + "), class "
                    + clazz.getName() + " is not subtype of interface.");
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
        org.apache.dubbo.common.Extension extension = clazz.getAnnotation(org.apache.dubbo.common.Extension.class);
        if (extension != null) {
            return extension.value();
        }

        String name = clazz.getSimpleName();
        if (name.endsWith(type.getSimpleName())) {
            name = name.substring(0, name.length() - type.getSimpleName().length());
        }
        return name.toLowerCase();
    }

}

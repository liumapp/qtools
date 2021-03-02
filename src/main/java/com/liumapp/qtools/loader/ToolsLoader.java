package com.liumapp.qtools.loader;

import com.liumapp.qtools.container.Holder;
import com.liumapp.qtools.core.annotations.SPI;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/**
 * @file ToolsLoader.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/1 20:45
 */
public class ToolsLoader<T> {

    private final Class<?> type;

    private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

    private static final ConcurrentMap<Class<?>, ToolsLoader<?>> TOOLS_LOADERS = new ConcurrentHashMap<>(64);

    private static final ConcurrentMap<Class<?>, Object> TOOLS_INSTANCES = new ConcurrentHashMap<>(64);

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    private String cachedDefaultName;

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



}

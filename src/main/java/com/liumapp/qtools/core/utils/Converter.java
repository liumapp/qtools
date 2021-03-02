package com.liumapp.qtools.core.utils;

import com.liumapp.qtools.core.annotations.SPI;
import com.liumapp.qtools.loader.ToolsLoader;
import com.liumapp.qtools.sort.Prioritized;


import static com.liumapp.qtools.classloader.ClassUtils.*;
import static com.liumapp.qtools.core.utils.TypeUtils.*;
/**
 * @file Converter.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/2 14:44
 */
@SPI
@FunctionalInterface
public interface Converter<S, T> extends Prioritized {

    /**
     * Accept the source type and target type or not
     *
     * @param sourceType the source type
     * @param targetType the target type
     * @return if accepted, return <code>true</code>, or <code>false</code>
     */
    default boolean accept(Class<?> sourceType, Class<?> targetType) {
        return isAssignableFrom(sourceType, getSourceType()) && isAssignableFrom(targetType, getTargetType());
    }

    /**
     * Convert the source-typed value to the target-typed value
     *
     * @param source the source-typed value
     * @return the target-typed value
     */
    T convert(S source);

    /**
     * Get the source type
     *
     * @return non-null
     */
    default Class<S> getSourceType() {
        return findActualTypeArgument(getClass(), Converter.class, 0);
    }

    /**
     * Get the target type
     *
     * @return non-null
     */
    default Class<T> getTargetType() {
        return findActualTypeArgument(getClass(), Converter.class, 1);
    }

    static Converter<?, ?> getConverter(Class<?> sourceType, Class<?> targetType) {
        return ToolsLoader.getToolsLoader(Converter.class)
                .getSupportedExtensionInstances()
                .stream()
                .filter(converter -> converter.accept(sourceType, targetType))
                .findFirst()
                .orElse(null);
    }

    static <T> T convertIfPossible(Object source, Class<T> targetType) {
        Converter converter = getConverter(source.getClass(), targetType);
        if (converter != null) {
            return (T) converter.convert(source);
        }
        return null;
    }
}
package com.liumapp.qtools.property.core.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.ConcurrentMap;

/**
 * file MapFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * A factory which creates {@link ConcurrentMap} instances.
 */
@FunctionalInterface
public interface MapFactory {

    /**
     * Create a new map instance for the given types
     *
     * @param <K> The key
     * @param <V> The value
     * @return A new map instance
     */
    @NonNull
    <K, V> ConcurrentMap<K, V> create();

}

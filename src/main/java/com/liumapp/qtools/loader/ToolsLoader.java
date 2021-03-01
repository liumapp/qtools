package com.liumapp.qtools.loader;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @file ToolsLoader.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/1 20:45
 */
public class ToolsLoader<T> {

    private final Class<?> type;

    private static final ConcurrentMap<Class<?>, Object> TOOLS_INSTANCES = new ConcurrentHashMap<>(64);

    private ToolsLoader(Class<?> type) {
        this.type = type;
    }



}

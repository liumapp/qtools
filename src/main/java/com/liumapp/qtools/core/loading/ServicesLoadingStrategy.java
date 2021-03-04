package com.liumapp.qtools.core.loading;

/**
 * @file ServicesLoadingStrategy.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/4 09:26
 */
public class ServicesLoadingStrategy implements LoadingStrategy {

    @Override
    public String directory() {
        return "META-INF/services/";
    }

    @Override
    public boolean overridden() {
        return true;
    }

    @Override
    public int getPriority() {
        return MIN_PRIORITY;
    }
}

package com.liumapp.qtools.loading;

/**
 * @file QtoolsLoadingStrategy.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/4 09:28
 */
public class QtoolsLoadingStrategy implements LoadingStrategy {

    @Override
    public String directory() {
        return "META-INF/qtools/";
    }

    @Override
    public boolean overridden() {
        return true;
    }

    @Override
    public int getPriority() {
        return NORMAL_PRIORITY;
    }
}

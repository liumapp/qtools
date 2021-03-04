package com.liumapp.qtools.core.loading;

import com.liumapp.qtools.core.sort.Prioritized;

/**
 * @file LoadingStrategy.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/2 10:52
 */
public interface LoadingStrategy extends Prioritized {

    String directory();

    default boolean preferExtensionClassLoader() {
        return false;
    }

    default String[] excludedPackages() {
        return null;
    }

    /**
     * Indicates current {@link LoadingStrategy} supports overriding other lower prioritized instances or not.
     *
     * @return if supports, return <code>true</code>, or <code>false</code>
     * @since 2.7.7
     */
    default boolean overridden() {
        return false;
    }

}

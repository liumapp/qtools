package com.liumapp.qtools.property.core.loader;

/**
 * file HeaderMode.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Modes which {@link ConfigurationLoader}s can use to manipulate headers when loading/saving.
 */
public enum HeaderMode {

    /**
     * Use the header loaded from an existing file, replacing any header set in the options
     */
    PRESERVE,

    /**
     * Ignore any header present in input, and output a header if one has been set in options
     */
    PRESET,

    /**
     * Ignore any header present in input, and do not output any header
     */
    NONE
}

package com.liumapp.qtools.core.annotations;

/**
 * @file ApplicationInitListener.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/2 16:55
 */
@SPI
public interface ApplicationInitListener {

    /**
     * init the application
     */
    void init();

}

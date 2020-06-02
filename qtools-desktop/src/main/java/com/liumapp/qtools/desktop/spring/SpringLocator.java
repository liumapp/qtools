package com.liumapp.qtools.desktop.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * file SpringLocator.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/6/2
 */
public class SpringLocator {

    public static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

}

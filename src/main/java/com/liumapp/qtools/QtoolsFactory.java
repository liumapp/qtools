package com.liumapp.qtools;

import com.liumapp.qtools.core.AbstractFactory;

/**
 * file QtoolsFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class QtoolsFactory extends AbstractFactory<Qtools> {

    private static QtoolsFactory INSTANCE;

    private QtoolsFactory() {
    }

    public static synchronized Qtools getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QtoolsFactory();
        }

        return INSTANCE.createInstanceIfNotExists();
    }

    @Override
    protected Qtools createInstance() {
        return new Qtools();
    }


}

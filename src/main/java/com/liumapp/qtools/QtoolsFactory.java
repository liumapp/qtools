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

    public static synchronized QtoolsFactory getFactoryInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QtoolsFactory();
        }
        return INSTANCE;
    }

    @Override
    public Qtools getInstance() {
        return this.createInstanceIfNotExists();
    }

    @Override
    public Qtools build() {
        this.t = INSTANCE.createInstance();
        return this.t;
    }

    @Override
    protected Qtools createInstance() {
        return new Qtools();
    }


}

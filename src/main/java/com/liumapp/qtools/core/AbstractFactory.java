package com.liumapp.qtools.core;

/**
 * file AbstractFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public abstract class AbstractFactory <T>{

    protected T t;

    public synchronized final T createInstanceIfNotExists() {
        if (this.t == null) {
          this.t = createInstance();
        }
        return this.t;
    }

    protected abstract T createInstance ();

    public abstract T build ();

    public abstract T getInstance ();

}

package com.liumapp.qtools.async.adder;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * file Adder.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/11/13
 */
@FunctionalInterface
public interface Adder<T>{

    void add(LinkedBlockingQueue queue, T t);

}
package com.liumapp.qtools.data.service;

import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * file Writer.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/8
 */
public interface Writer<E> extends Ready,WorkHandler<E> {
    public void setDisruptor(Disruptor<E> disruptor);
}

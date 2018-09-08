package com.liumapp.qtools.data.service;

import com.liumapp.qtools.data.core.Field;
import com.lmax.disruptor.RingBuffer;

/**
 * file Reader.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/8
 */
public interface Reader<E> extends Ready {

    public void read() throws Exception;

    public void setRingBuffer(RingBuffer ringBuffer);

    public boolean hasRemaining();

    public Reader<E> setField(Field field);

}

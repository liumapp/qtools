package com.liumapp.qtools.async.adder;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * file BlockingAdder.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/11/13
 */
@Slf4j
public class BlockingAdder<T> implements Adder<T>{
    private long timeout;
    private TimeUnit unit;

    public BlockingAdder(long timeout, TimeUnit unit) {
        this.timeout = timeout;
        this.unit = unit;
    }

    @Override
    public void add(LinkedBlockingQueue queue, T t) {
        try {
            queue.offer(t,timeout,unit);
        } catch (InterruptedException e) {
            log.error("",e);
        }
    }
}

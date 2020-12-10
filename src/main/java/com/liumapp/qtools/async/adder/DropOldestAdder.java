package com.liumapp.qtools.async.adder;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * file DropOldestAdder.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/11/13
 */
@Slf4j
public class DropOldestAdder<T> implements Adder<T>{

    private AtomicLong dropNumber = new AtomicLong();

    private Integer maxBufferSizePreProcess;

    public DropOldestAdder() {
    }

    public DropOldestAdder(Integer maxBufferSizePreProcess) {
        this.maxBufferSizePreProcess = maxBufferSizePreProcess;
    }

    public Integer getMaxBufferSizePreProcess() {
        return maxBufferSizePreProcess;
    }

    public DropOldestAdder<T> setMaxBufferSizePreProcess(Integer maxBufferSizePreProcess) {
        this.maxBufferSizePreProcess = maxBufferSizePreProcess;
        return this;
    }

    @Override
    public void add(LinkedBlockingQueue queue, T t) {
        if (queue.size() >= maxBufferSizePreProcess) {
            dropNumber.getAndIncrement();
            if (dropNumber.get() % 100 == 0) {
                log.error("任务堆现已溢出100条指令，开始清空此任务堆积攒的任务");
                queue.clear();
            }
            queue.remove();
        }
        queue.add(t);
    }
}
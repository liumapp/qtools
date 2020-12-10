package com.liumapp.qtools.async;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.liumapp.qtools.async.adder.Adder;
import com.liumapp.qtools.async.adder.BlockingAdder;
import com.liumapp.qtools.async.adder.DropOldestAdder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * file AsyncProcess.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/11/13
 */
@Slf4j
public class AsyncProcess <T> {
    private int maxProcessCount;
    private int maxBufferSizePreProcess;
    private Function<T,Boolean> process;
    private Function<List<T>,Boolean> batchProcess;
    private Adder<T> adder;
    private Map<Long, LinkedBlockingQueue<T>> mapQueue = new HashMap<>();

    public static  <P> Builder<P> newBuilder(){
        return new Builder<>();
    }

    public static class Builder<T>{
        private int maxProcessCount = 30;
        private int maxBufferSizePreProcess = 1000;
        private Function<T,Boolean> process;
        private Function<List<T>,Boolean> batchProcess;
        private Adder<T> adder;
        private Long timeout;
        private TimeUnit unit;

        private Builder(){
        }

        public Builder setMaxProcessCount(int maxProcessCount) {
            this.maxProcessCount = maxProcessCount;
            return this;
        }

        public Builder setMaxBufferSizePreProcess(int maxBufferSizePreProcess) {
            this.maxBufferSizePreProcess = maxBufferSizePreProcess;
            return this;
        }

        public Builder setProcess(Function<T, Boolean> process) {
            this.process = process;
            return this;
        }

        public Builder setBatchProcess(Function<List<T>, Boolean> batchProcess) {
            this.batchProcess = batchProcess;
            return this;
        }

        public Builder setAdder(Adder<T> adder) {
            this.adder = adder;
            return this;
        }

        public Builder setAddBlockTimeout(long timeout,TimeUnit unit){
            this.timeout = timeout;
            this.unit = unit;
            return this;
        }

        public AsyncProcess<T> build(){
            Preconditions.checkState(this.process != null || this.batchProcess != null,"process can not be null");

            AsyncProcess<T> AsyncProcess = new AsyncProcess<> ();
            if(this.adder ==null){
                if(timeout!= null && unit!=null){
                    this.adder = new BlockingAdder<>(timeout,unit);
                }else{
                    this.adder = new DropOldestAdder<>();
                }
            }

            AsyncProcess.process = this.process;
            AsyncProcess.batchProcess = this.batchProcess;
            AsyncProcess.adder = this.adder;
            AsyncProcess.maxBufferSizePreProcess = this.maxBufferSizePreProcess;
            AsyncProcess.maxProcessCount = this.maxProcessCount;
            if(this.process != null) {
                AsyncProcess.start();
            }else if(this.batchProcess != null){
                AsyncProcess.startBatch();
            }
            return AsyncProcess;
        }

    }

    public void add(T t,Long channel){
        long m = Math.abs(channel%maxProcessCount);
        LinkedBlockingQueue queue = mapQueue.get(m);
        adder.add(queue,t);
    }

    private void start(){
        ExecutorService executorService = Executors.newFixedThreadPool(maxProcessCount);

        for(long i = 0; i< maxProcessCount; i++){
            final LinkedBlockingQueue<T> queue =  new LinkedBlockingQueue<>(maxBufferSizePreProcess);
            mapQueue.put(i,queue);
            executorService.submit(new Runnable() {
                private LinkedBlockingQueue<T> blockingQueue = queue;
                @Override
                public void run() {
                    while (true) {
                        try {
                            T t = blockingQueue.take();
                            process.apply(t);
                        } catch (Throwable e) {
                            log.error("",e);
                        }
                    }
                }
            });

        }
    }

    private void startBatch(){
        ExecutorService executorService = Executors.newFixedThreadPool(maxProcessCount);

        for(long i = 0; i< maxProcessCount; i++){
            final LinkedBlockingQueue<T> queue =  new LinkedBlockingQueue(maxBufferSizePreProcess);
            mapQueue.put(i,queue);
            executorService.submit(new Runnable() {
                private LinkedBlockingQueue<T> blockingQueue = queue;
                private LinkedList<T> batch = new LinkedList<>();
                @Override
                public void run() {
                    while (true) {
                        try {
                            int size = blockingQueue.size();
                            if(size>0){
                                for(int i=0;i<size;i++){
                                    batch.add(blockingQueue.take());
                                }
                                batchProcess.apply(batch);
                                batch.clear();
                            }else{
                                batch.add( blockingQueue.take());
                                batchProcess.apply(batch);
                                batch.clear();
                            }
                        } catch (Throwable e) {
                            log.error("",e);
                        }
                    }
                }
            });

        }
    }


}

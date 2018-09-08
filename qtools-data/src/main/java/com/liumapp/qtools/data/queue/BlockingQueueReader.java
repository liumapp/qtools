package com.liumapp.qtools.data.queue;

import com.liumapp.qtools.data.core.Field;
import com.liumapp.qtools.data.core.Row;
import com.liumapp.qtools.data.service.Reader;
import com.lmax.disruptor.RingBuffer;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * file BlockingQueueReader.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/8
 */
public class BlockingQueueReader implements Reader<Row> {

    private BlockingQueue<Row> queue;

    private boolean hasRemaining = true;

    private RingBuffer<Row> ringBuffer ;

    private Field field ;


    @Override
    public void read() throws Exception {

        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            Row row = ringBuffer.get(sequence); // Get the entry in the Disruptor
            if(!row.isCanWrite()) row.setCanWrite(true);
            if(row.getField()==null&&this.field!=null) {
                row.setField((Field)field.clone());
            }
            // for the sequence
            if(!setRow(row)){
                hasRemaining=false;
                System.out.println("hasRemaining:"+hasRemaining);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            if(hasRemaining) ringBuffer.publish(sequence);
        }
    }

    private boolean setRow(Row row) throws InterruptedException {
        if(this.queue==null) throw new RuntimeException("ERR:queue can not be null!");
        Row out = this.queue.take();
        if(out.isBlank()) return  false;

        row.setColumn(out);
        out = null;
        return true;
    }

    @Override
    public void setRingBuffer(RingBuffer ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    @Override
    public boolean hasRemaining() {
        return hasRemaining;
    }

    @Override
    public Reader<Row> setField(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public void before() throws Exception {

    }

    @Override
    public void after() throws Exception {

    }

    public Queue<Row> getQueue() {
        return queue;
    }

    public BlockingQueueReader setQueue(BlockingQueue<Row> queue) {
        this.queue = queue;
        return this;
    }
}

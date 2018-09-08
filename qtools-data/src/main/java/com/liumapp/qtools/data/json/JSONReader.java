package com.liumapp.qtools.data.json;

import com.liumapp.qtools.data.core.Field;
import com.liumapp.qtools.data.core.Row;
import com.liumapp.qtools.data.helper.RowSetter;
import com.liumapp.qtools.data.service.Reader;
import com.lmax.disruptor.RingBuffer;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * file JSONReader.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/8
 */
public class JSONReader implements Reader<Row> {

    private Field field;

    private java.io.BufferedReader ioReader;

    private boolean hasRemaining  = true;

    private RingBuffer<Row> ringBuffer;

    private static String format = "yyyy-MM-dd hh:mm:ss.SSS";

    @Override
    public void read() throws Exception {

        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            Row row = ringBuffer.get(sequence); // Get the entry in the Disruptor
            if(!row.isCanWrite()) row.setCanWrite(true);
            if(row.getField()==null) {
                row.setField((Field)field.clone());
            }
            // for the sequence
            setRow(row, field);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            if(hasRemaining) ringBuffer.publish(sequence);
        }

    }

    private void setRow(Row row,Field field) throws IOException {

        if (row == null)
            throw new RuntimeException("row is null!");

        String line = ioReader.readLine();
        if (line!=null) {
            int i[] = new int[]{0};
            field.forEach((k, v) -> {
                Class c = (Class) field.getType(k);

                try {
                    JSONObject jsonObject = new JSONObject(line);
                    String s = jsonObject.get(k.toString()).toString();
                    RowSetter.setRow(row,c,s,k,format);
                    i[0] += 1;
                }catch (Exception e){
                    e.printStackTrace();
                    System.err.println(line);
                }
            });
        } else {
            hasRemaining = false;
        }
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
        ioReader.close();
    }

    public JSONReader setIoReader(java.io.Reader ioReader) {
        this.ioReader = new BufferedReader(ioReader);
        return this;
    }

    public static void setFormat(String format) {
        JSONReader.format = format;
    }
}

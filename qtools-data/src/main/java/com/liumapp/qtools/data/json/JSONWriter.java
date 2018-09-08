package com.liumapp.qtools.data.json;

import com.liumapp.qtools.data.core.Row;
import com.liumapp.qtools.data.service.Each;
import com.liumapp.qtools.data.service.Writer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * file JSONWriter.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/8
 */
public class JSONWriter implements Writer<Row>, Each<Row> {
    @Override
    public void dealEach(Row row) throws Exception {

    }

    @Override
    public void setDisruptor(Disruptor<Row> disruptor) {

    }

    @Override
    public void before() throws Exception {

    }

    @Override
    public void after() throws Exception {

    }

    @Override
    public void onEvent(Row row) throws Exception {

    }
}

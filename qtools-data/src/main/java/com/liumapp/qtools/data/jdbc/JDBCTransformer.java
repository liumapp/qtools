package com.liumapp.qtools.data.jdbc;

import com.liumapp.qtools.data.core.Row;
import com.liumapp.qtools.data.service.Transformer;

/**
 * file JDBCTransformer.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/8
 */
public class JDBCTransformer implements Transformer<Row> {

    @Override
    public void onEvent(Row event, long sequence, boolean endOfBatch) throws Exception {

        System.out.print(sequence+"\t"+endOfBatch+"\t"+event);
    }

}

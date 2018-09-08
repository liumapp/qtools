package com.liumapp.qtools.data.factory;

import com.liumapp.qtools.data.core.Row;

/**
 * file RowFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/8
 */
public class RowFactory implements Factory<Row> {

    @Override
    public Row newInstance() {
        return new Row();
    }

}

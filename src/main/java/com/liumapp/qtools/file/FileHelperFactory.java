package com.liumapp.qtools.file;

import com.liumapp.qtools.core.AbstractFactory;
import com.liumapp.qtools.file.core.FileHelper;

/**
 * file FileHelperFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class FileHelperFactory extends AbstractFactory<FileHelper> {

    private static FileHelperFactory INSTANCE;

    private FileHelperFactory() {
    }

    public static synchronized FileHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileHelperFactory();
        }
        return INSTANCE.createInstanceIfNotExists();
    }

    @Override
    protected FileHelper createInstance() {
        return new SimpleFileHelper();
    }


}

package com.liumapp.qtools.file;

import com.liumapp.qtools.file.core.FileHelper;

/**
 * file FileHelperFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class FileHelperFactory {

    private FileHelper fileHelper;

    private static FileHelperFactory INSTANCE;

    private FileHelperFactory() {
    }

    public static synchronized FileHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileHelperFactory();
            building(INSTANCE);
        }
        return INSTANCE.fileHelper;
    }

    private static void building (FileHelperFactory instance) {
        instance.fileHelper = new SimpleFileHelper();
    }


}

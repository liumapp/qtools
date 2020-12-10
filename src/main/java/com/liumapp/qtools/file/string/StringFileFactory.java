package com.liumapp.qtools.file.string;

import com.liumapp.qtools.file.string.core.StringFile;

/**
 * file StringFileFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class StringFileFactory {


    private StringFile stringFile;

    private static StringFileFactory INSTANCE;

    private StringFileFactory() {
    }

    public static synchronized StringFile getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StringFileFactory();
            building(INSTANCE);
        }
        return INSTANCE.stringFile;
    }

    private static void building (StringFileFactory instance) {
        instance.stringFile = new Base64StringFileHelper();
    }


}

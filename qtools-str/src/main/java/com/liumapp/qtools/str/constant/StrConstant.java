package com.liumapp.qtools.str.constant;

import java.io.File;
import java.nio.charset.Charset;

/**
 * author liumapp
 * file StrConstant.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/13
 */
public final class StrConstant {

    private StrConstant () {
        throw new UnsupportedOperationException("unsupported to initialize");
    }

    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);

    public static final String FILE_SEP = File.separator;

    public static final String FILE_PATH_SEP = File.pathSeparator;

}

package com.liumapp.qtools.str.basic;

/**
 * @author liumapp
 * @file StrTool.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2018/8/8
 */
public class StrTool {

    private StrTool () {
        throw new UnsupportedOperationException("not allowed to initialize");
    }

    /**
     * is null or empty
     * @param s string
     * @return boolean true/false
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

}

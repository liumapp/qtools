package com.liumapp.qtools.file.base64;

/**
 * file CheckBase64Tool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/29
 */
public class CheckBase64Tool {

    /**
     * is the header info of base64 exists.
     */
    public static Boolean checkHeader (String base64Content) {
        String[] baseStr = base64Content.split(",");
        return true;
    }

}

package com.liumapp.qtools.security.encrypt;

/**
 * file Sha1Tool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/1
 */
public class Sha1Tool {

    private Sha1Tool() {
        throw new UnsupportedOperationException("not allowed to initialize");
    }

    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }


}

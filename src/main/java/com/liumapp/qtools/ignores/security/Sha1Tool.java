package com.liumapp.qtools.ignores.security;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    /**
     * use apache commons codec to convert
     * @param convertme byte[]
     * @return String
     */
    public static String toSHA1(byte[] convertme) {
        return DigestUtils.sha1Hex(convertme);
    }

    public static String toSHA1(String convertme) {
        return DigestUtils.sha1Hex(convertme);
    }

    public static byte[] toSHA1(InputStream is) throws IOException {
        return DigestUtils.sha1(is);
    }

    /**
     * use old method to convert
     * @param convertme byte[]
     * @return String
     */
    public static String toSHA1Old (byte[] convertme) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return byteArrayToHexString(md.digest(convertme));
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

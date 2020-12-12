package com.liumapp.qtools.file.helper;

import com.liumapp.qtools.file.core.Base64Helper;

/**
 * file CommonBase64Helper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/12
 */
public class CommonBase64Helper implements Base64Helper {

    @Override
    public byte[] stringToBytes(String base64) {
        
        return new byte[0];
    }

    @Override
    public String header(String base64) {
        String[] baseStr = base64.split(";base64,");
        if (baseStr.length == 2) {
            return baseStr[0];
        }
        return null;
    }

    @Override
    public String removeHeader(String base64) {
        String[] baseStr = base64.split(";base64,");
        if (baseStr.length == 2) {
            return baseStr[1];
        }
        return base64;
    }
}

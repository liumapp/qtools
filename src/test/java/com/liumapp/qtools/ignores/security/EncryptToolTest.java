package com.liumapp.qtools.ignores.security;

import org.junit.Test;

/**
 * file EncryptUtilTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/4
 */
public class EncryptToolTest {

    @Test
    public void testEncrptyAndDecode () throws Exception {
        EncryptTool encryptUtil = new EncryptTool("idfjiefjoiwejfwefjiejfi", null);
        String phoneAndCompanyId = "15757125631_3";
        String result = encryptUtil.encode(phoneAndCompanyId);
        System.out.println("this is encoded string : " + result);
        String decodeStr = encryptUtil.decode(result);
        System.out.println("this is decode string : " + decodeStr);
    }

}

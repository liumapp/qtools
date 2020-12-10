package com.liumapp.qtools.security;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * file Sha256Tool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/2/15
 */
public class Sha256Tool {

    public static byte[] toSha256Bytes (String hashMe) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(hashMe.getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static String toSha256String (String hashMe) {
        String hash256hex = DigestUtils.sha256Hex(hashMe);
        return hash256hex;
    }

}

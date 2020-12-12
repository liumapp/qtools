package com.liumapp.qtools.file.core;

import com.liumapp.qtools.core.Helper;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * file Base64Helper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/11
 */
public interface Base64Helper extends Helper {

    /**
     * convert base64 content bytes to a new ByteBuffer
     * @param base64Bytes base64 content bytes
     * @return
     */
//    ByteBuffer bytesToByteBuffer (byte[] base64Bytes);

    /**
     * convert base64 content string to a new ByteBuffer
     * @param base64 base64 content string
     * @return
     */
//    ByteBuffer stringToByteBuffer (String base64);

    /**
     * convert base64 content string to byte array
     * @param base64 string
     * @return
     */
    byte[] stringToBytes (String base64);

    byte[] stringToBytes (String base64, Charset charset);

    /**
     * return the base64 header if exists
     * @param base64 string
     * @return
     */
    String header (String base64);

    /**
     * remove the base64 header if exists
     * @param base64 string
     * @return base64 content without header
     */
    String removeHeader (String base64);



}

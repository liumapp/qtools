package com.liumapp.qtools.file.core;

import com.liumapp.qtools.core.Helper;

import java.nio.ByteBuffer;

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
    ByteBuffer bytesToByteBuffer (byte[] base64Bytes);

    /**
     * convert base64 content string to a new ByteBuffer
     * @param base64 base64 content string
     * @return
     */
    ByteBuffer stringToByteBuffer (String base64);



}

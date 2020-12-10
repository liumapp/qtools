package com.liumapp.qtools.file;

import com.liumapp.qtools.file.core.FileHelper;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

/**
 * file SimpleFileHelper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class SimpleFileHelper implements Serializable, FileHelper {


    private static final long serialVersionUID = -7265612281189715204L;

    protected SimpleFileHelper() {
    }

    @Override
    public byte[] readyBytesByFilePath(String filePath) throws IOException {
        FileChannel channel = new RandomAccessFile(filePath, "r").getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] oldBytes = new byte[0];
        try {
            while (channel.read(byteBuffer) != -1) {
                Integer readLength = byteBuffer.position();
                byteBuffer.rewind();
                byte[] newBytes = new byte[readLength];
                byteBuffer.get(newBytes);
                byteBuffer.clear();
                byte[] mergeBytes = new byte[oldBytes.length + newBytes.length];
                System.arraycopy(oldBytes,0,mergeBytes,0,oldBytes.length);
                System.arraycopy(newBytes,0,mergeBytes,oldBytes.length,newBytes.length);
                oldBytes = mergeBytes;
            }
        } finally {
            if (channel.isOpen()) {
                channel.close();
            }
        }

        return oldBytes;
    }
}

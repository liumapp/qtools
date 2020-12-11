package com.liumapp.qtools.file.helper;

import com.liumapp.qtools.file.FileHelperParam;
import com.liumapp.qtools.file.core.AbstractFileHelper;
import com.liumapp.qtools.file.core.FileHelper;
import com.liumapp.qtools.file.core.annotations.IOType;
import com.liumapp.qtools.file.core.enums.IOEnum;
import com.liumapp.qtools.file.core.exceptions.ReadBytesFaildException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * file NioFileHelper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/11
 */
@IOType(IOEnum.NIO)
public class NioFileHelper extends AbstractFileHelper implements FileHelper, Serializable {

    private static final long serialVersionUID = -1274329871698769820L;

    public NioFileHelper(FileHelperParam param) {
        this.fileHelperParam = param;
    }

    @Override
    public byte[] readyBytesByFilePath(String filePath) {
        byte[] oldBytes = new byte[0];
        try {
            FileChannel channel = new RandomAccessFile(filePath, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

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
        } catch (IOException e) {
            throw new ReadBytesFaildException(e.getMessage());
        }
        return oldBytes;
    }

    @Override
    public void writeBytesToFile(byte[] bytes, URL filePath) {

    }
}

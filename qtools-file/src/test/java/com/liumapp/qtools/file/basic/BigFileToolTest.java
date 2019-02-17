package com.liumapp.qtools.file.basic;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * file BigFileToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/2/16
 */
public class BigFileToolTest extends TestCase {

    @Test
    public void testWriteBigFile () throws IOException {
        byte[] buffer = "test content for writing long file \n".getBytes();
        int number_of_lines = 8_000_000;

        FileChannel rwChannel = new RandomAccessFile("testfile.txt", "rw").getChannel();
        ByteBuffer wrBuf = rwChannel.map(FileChannel.MapMode.READ_WRITE, 0, buffer.length * number_of_lines);
        for (int i = 0; i < number_of_lines; i++)
        {
            wrBuf.put(buffer);
        }
        rwChannel.close();
    }

}

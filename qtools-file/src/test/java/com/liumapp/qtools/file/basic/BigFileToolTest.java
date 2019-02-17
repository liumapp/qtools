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
        String str = "test content for writing long file \n";
        long number_of_lines = 800_000_000;

        BigFileTool.createBigFileWithRepeatRow("testfile.txt", str, number_of_lines);
    }

}

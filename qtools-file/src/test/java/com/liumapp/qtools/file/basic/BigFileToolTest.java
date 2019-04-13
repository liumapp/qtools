package com.liumapp.qtools.file.basic;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * file BigFileToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/2/16
 */
public class BigFileToolTest extends TestCase {

    /**
     * 创建一个2.67G大小的文件，用时2s613ms（i7）
     * @throws IOException
     */
    @Test
    public void testWriteBigFile () throws IOException {
        String str = "test content for writing long file \n";
        long number_of_lines = 80_000_000;
        BigFileTool.createBigFileWithRepeatRow("testfile.txt", str, number_of_lines);
        if (FileTool.isFileExists(new File("testfile.txt"))) {
            FileTool.deleteFile(new File("testfile.txt"));
        }
    }

}

package com.liumapp.qtools.file.basic;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * file BigFileTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/2/16
 */
public class BigFileTool {

    /**
     * 在给定的文件中，重复创建指定行数的内容，来达到创建大文件的目的
     * @param path 文件路径
     * @param singleLineContent 一行的内容
     * @param rows 要重复的行数
     * @return boolean
     * @throws IOException ioException
     */
   public static boolean createBigFileWithRepeatRow (String path, String singleLineContent, Long rows) throws IOException {
       FileOutputStream fosRef = new FileOutputStream(new File(path));
       FileChannel fileChannel = fosRef.getChannel();

       ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 20);//分配20M的缓冲区
       //判断缓冲区一次可以写入多少行
       int rowInBuffer = (int) Math.floor(1024 * 1024 * 20 / (singleLineContent.getBytes().length));
       //组装缓冲区内容
       
       //判断需要刷新多少次缓冲区
       int repeats = (int) Math.ceil(rows / rowInBuffer);
       for (long i = 0 ; i < repeats ; i++) {
            buffer.put(singleLineContent.getBytes());
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
       }

       fileChannel.close();
       fosRef.close();
       return true;
   }

}

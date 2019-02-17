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

   public static boolean createBigFileWithRepeatRow (String path, String singleLineContent, Long rows) throws IOException {
       FileOutputStream fosRef = new FileOutputStream(new File(path));
       FileChannel fileChannel = fosRef.getChannel();

       ByteBuffer buffer = ByteBuffer.allocate(2048);
       for (long i = 0 ; i < rows ; i++) {
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

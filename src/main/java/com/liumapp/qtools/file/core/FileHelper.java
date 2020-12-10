package com.liumapp.qtools.file.core;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * file FileHelper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public interface FileHelper {

    /**
     * read file content by nio
     * @param filePath absolute file path
     * @return
     */
    public byte[] readyBytesByFilePath(String filePath) throws IOException;

//    public

}

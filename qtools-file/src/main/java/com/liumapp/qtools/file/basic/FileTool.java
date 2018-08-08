package com.liumapp.qtools.file.basic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author liumapp
 * @file FileTool.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2018/8/7
 */
public final class FileTool {

    /**
     * all static method , plz do not initialize the class
     */
    private FileTool() {
        throw new UnsupportedOperationException("not allowed to initialize");
    }

    /**
     * write string to a new file
     * @param file File object
     * @param content String content
     * @return boolean true write success : false
     */
    public static boolean writeStringToNewFile (File file, String content) throws IOException {
        if (file == null || content == null) {
            return false;
        }
        if (!createFile(file)) {
            return false;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(content);
            return true;
        }
    }

    /**
     * create a file , and the directory needs .
     * @param file file object
     * @return true/false
     */
    public static boolean createFile (File file) throws IOException {
        if (file == null) {
            return false;
        }
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) {
            return file.isFile();
        }
        return createDir(file.getParentFile()) && file.createNewFile();
    }

    /**
     * create a file directory
     * @param file file object
     * @return true/false
     */
    public static boolean createDir (File file) throws IOException {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    public static boolean deleteFile (String srcFilePath) {
        return deleteFile()
    }

    public static boolean deleteFile (File file) {

    }

}

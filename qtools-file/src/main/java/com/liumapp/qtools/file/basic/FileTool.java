package com.liumapp.qtools.file.basic;

import com.liumapp.qtools.str.basic.StrTool;

import java.io.*;
import java.util.Random;

/**
 * author liumapp
 * file FileTool.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/7
 */
public class FileTool {

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
     * create a file from input stream to savepath.
     */
    public static boolean createFileFromInputStream (InputStream is, String savePath) throws IOException {
        if (is == null || savePath == null)
            return false;
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        File targetFile = new File(savePath);
        createFile(targetFile);
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
        outStream.close();
        is.close();
        return true;
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
        if (file.exists()) {
            return file.isFile();
        }
        return createDir(file.getParentFile()) && file.createNewFile();
    }

    /**
     * create a file , and the direcotory needs
     * @return File object
     */
    public static File createFileObject (String savePath, String saveName) throws IOException {
        File file = new File(savePath + "/" +saveName);
        createFile(file);
        return file;
    }

    /**
     * delete a file by file path
     * @param srcFilePath file save path
     * @return true: deleted success
     */
    public static boolean deleteFile (String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * delete a file
     * @param file file object
     * @return true : deleted success
     */
    public static boolean deleteFile (File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * create a file directory
     * @param file file object
     * @return true/false
     */
    public static boolean createDir (File file) throws IOException {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * delete dir
     * @param dirPath string dir path
     * @return {@code true}: delete success <br> {@code false}: delete failed
     */
    public static boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * delete dir
     * @param dir dir folder
     * @return {@code true}: delete success <br> {@code false}: delete failed
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        deleteFilesInDir(dir);
        return dir.delete();
    }

    /**
     * is file exists
     *
     * @param filePath file save path
     * @return {@code true}: exists <br> {@code false}: not exists
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * is file exists
     *
     * @param file file object
     * @return {@code true}: exists <br> {@code false}: not exists
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * get a file object by file savepath
     * @param filePath file savepath
     * @return file object
     */
    public static File getFileByPath(String filePath) {
        return StrTool.isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * is a direcotory according to a filepath.
     */
    public static boolean isDirectory (String filePath) {
        File file = new File(filePath);
        return file.isDirectory();
    }

    /**
     * delete all files in a specified folder
     * @param dir folder path
     * @return {@code true}: delete success <br> {@code false}: delete failed
     */
    public static boolean deleteFilesInDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}

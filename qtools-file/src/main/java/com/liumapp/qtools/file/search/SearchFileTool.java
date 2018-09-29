package com.liumapp.qtools.file.search;

import com.liumapp.qtools.file.basic.FileTool;
import com.liumapp.qtools.file.filter.SimpleFileFilterTool;

import java.io.File;

/**
 * file SearchFileTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/29
 */
public class SearchFileTool {

    /**
     * search files in an appointed path with begin string and end string
     */
    public static File[] searchFiles (String searchPath, String beginString, String endString) {

    }

    /**
     * search files in an appointed path with begin string
     */
    public static File[] searchFiles (String searchPath, String beginString) {

    }

    /**
     * search files in an appointed path with end string
     */
    public static File[] searchFiles (String searchPath, String endString) {

    }

    /**
     * does file in an appointed path with begin string and end string exists ?
     */
    public static String hasFile (String searchPath, String beginString, String endString) {

    }

    /**
     * does file in an appointed path with begin string exists ?
     */
    public static String hasFile (String searchPath, String beginString) {

    }

    /**
     * does file in an appointed path with end string exists ?
     */
    public static String hasFile (String searchPath, String endString) {

    }

    /**
     *查找文件名称结尾为.txt的文件是否存在
     *filepath  文件路径
     *filename  文件名包含指定字符如：log_
     **/
    public static String getFileName (String filepath, String filename) {
        File[] fileArray = getFileList(filepath, filename);
        if (fileArray == null) return null;
        for (int i = 0; i < fileArray.length; i++) {
            if ((fileArray[i] != null) && fileArray[i].getName().endsWith(".txt")) {
                System.out.println("文件存在");
                return fileArray[i].getName();
            }
        }
        System.out.println("文件不存在");
        return null;
    }

    public static File[] getFileList(String filePath, String str) {
        FileTool.isFileExists(filePath);
    }

    /**
     * get all the files in an appointed path with begin string
     */
    private static File[] getList(String filePath, String str) {
        File file = new File(filePath);
        int j = 0;
        File[] fileArray = file.listFiles(new SimpleFileFilterTool(str));
        if (fileArray.length == 0)
            return null;
        File[] fileArrays = new File[fileArray.length];
        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i] != null) {
                    fileArrays[j] = fileArray[i];
                    j++;
                    if (j == 2) break;
                }
            }
        }
        return fileArrays;
    }




}

package com.liumapp.qtools.file.ignores;

import com.liumapp.qtools.ignores.array.ArrayTool;
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
        return getFileList(searchPath, beginString, endString);
    }

    /**
     * search files in an appointed path with begin string
     */
    public static File[] searchFilesWithBeginString (String searchPath, String beginString) {
        return getFileList(searchPath, beginString);
    }

    /**
     * search files in an appointed path with end string
     */
    public static File[] searchFilesWithEndString (String searchPath, String endString) {
        return getFileList(searchPath, "", endString);
    }

    /**
     * does file in an appointed path with begin string and end string exists ?
     * if exists return file name
     */
    public static String[] hasFile (String searchPath, String beginString, String endString) {
        File[] fileLists = searchFiles(searchPath, beginString, endString);
        if (fileLists == null)
            return null;
        String[] names = new String[fileLists.length];
        for (int i = 0; i< fileLists.length; i++) {
            names[i] = fileLists[i].getName();
        }
        return names;
    }

    /**
     * does file in an appointed path with begin string exists ?
     */
    public static String[] hasFileWithBeginString (String searchPath, String beginString) {
        File[] fileLists = searchFilesWithBeginString(searchPath, beginString);
        if (fileLists == null)
            return null;
        String[] names = new String[fileLists.length];
        for (int i = 0; i< fileLists.length; i++) {
            names[i] = fileLists[i].getName();
        }
        return names;
    }

    /**
     * does file in an appointed path with end string exists ?
     */
    public static String[] hasFileWithEndString (String searchPath, String endString) {
        File[] fileLists = searchFilesWithEndString(searchPath, endString);
        if (fileLists == null)
            return null;
        String[] names = new String[fileLists.length];
        for (int i = 0; i< fileLists.length; i++) {
            names[i] = fileLists[i].getName();
        }
        return names;
    }

    /**
     * get all the files in an appointed path with begin string
     */
    public static File[] getFileList (String filePath, String beginString) {
        if (FileTool.isDirectory(filePath))
            return getList(filePath, beginString);
        return null;
    }

    /**
     * get all the files in an appointed path with begin string and end string
     */
    public static File[] getFileList (String filePath, String beginString, String endString) {
        if (FileTool.isDirectory(filePath))
            return getList(filePath, beginString, endString);
        return null;
    }

    private static File[] getList (String filePath, String beginString) {
        File file = new File(filePath);
        return file.listFiles(new SimpleFileFilterTool(beginString));
    }

    private static File[] getList (String filePath, String beginString, String endString) {
        File file = new File(filePath);
        int j = 0;
        File[] fileArray = file.listFiles(new SimpleFileFilterTool(beginString));
        if (fileArray.length == 0)
            return null;
        File[] resultArrays = new File[fileArray.length];
        if (fileArray != null) {
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i] != null && fileArray[i].getName().endsWith(endString)) {
                    resultArrays[j] = fileArray[i];
                    j++;
                }
            }
        }
        return (File[]) ArrayTool.clearArray(resultArrays);
    }

}

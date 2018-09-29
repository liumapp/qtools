package com.liumapp.qtools.file.search;

import com.liumapp.qtools.file.filter.FileFilterTool;

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
     *查找文件名称结尾为.txt的文件是否存在
     *filepath  文件路径
     *filename  文件名包含指定字符如：log_
     **/
    public String getFileName (String filepath, String filename) {
        File[]fileArray = getFileList(filepath, filename);
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

    /***获取filepath路径下包含str字符的文件, ***/
    public static File [] getFileList(String filePath, String str) {
        File file = new File(filePath);
        File [] fileArrays = new File[2];
        int j = 0;
        //获取该目录下所有文件和目录的绝对路径
        File [] fileArray = file.listFiles(new FileFilterTool(str));
        if (fileArray != null){
            for (int i = 0; i < fileArray.length; i++) {
                if (fileArray[i] != null && fileArray[i].getName().contains(str)){
                    fileArrays[j] = fileArray[i];
                    j++;
                    if (j == 2) break;
                }
            }
        }
        return fileArrays;
    }




}

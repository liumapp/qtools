package com.liumapp.qtools.file.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * file FileFilterTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/29
 */
public class FileFilterTool implements FileFilter {

    private String filename = "";

    public FileFilterTool (String fileName) {
        this.filename = fileName;
    }

    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory()){
            return true;
        }else {
            String name = pathname.getName();
            if (name.startsWith(filename)){
                return true;
            }else {
                return false;
            }
        }
    }

}

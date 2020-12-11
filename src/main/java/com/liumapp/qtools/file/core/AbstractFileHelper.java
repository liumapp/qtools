package com.liumapp.qtools.file.core;

import com.liumapp.qtools.file.FileHelperParam;

/**
 * file AbstractFileHelper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public abstract class AbstractFileHelper {

    protected FileHelperParam fileHelperParam;

    public FileHelperParam getFileHelperParam() {
        return fileHelperParam;
    }

    public AbstractFileHelper setFileHelperParam(FileHelperParam fileHelperParam) {
        this.fileHelperParam = fileHelperParam;
        return this;
    }
}

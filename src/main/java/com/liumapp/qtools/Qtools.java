package com.liumapp.qtools;


import com.liumapp.qtools.file.FileHelperFactory;
import com.liumapp.qtools.file.core.FileHelper;
import com.liumapp.qtools.file.core.SupportFileHelper;

import java.io.Serializable;

/**
 * file Qtools.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class Qtools implements Serializable, SupportFileHelper {

    private static final long serialVersionUID = 5563619718620494936L;

    private FileHelperFactory fileHelperFactory;

    protected Qtools() {
        this.fileHelperFactory = FileHelperFactory.getFactoryInstance();
    }

    @Override
    public FileHelper getSingleFileHelper() {
        return fileHelperFactory.build();
    }

    @Override
    public FileHelperFactory newFileHelperBuild() {
        return this.fileHelperFactory;
    }
}

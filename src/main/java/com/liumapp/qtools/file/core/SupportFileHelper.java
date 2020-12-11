package com.liumapp.qtools.file.core;

import com.liumapp.qtools.file.FileHelperFactory;

/**
 * file SupportFileHelper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public interface SupportFileHelper {


    public FileHelper getSingleFileHelper();

    public FileHelperFactory newFileHelperBuild();


}

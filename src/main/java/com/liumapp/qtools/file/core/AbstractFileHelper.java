package com.liumapp.qtools.file.core;

import com.liumapp.qtools.core.Helper;
import com.liumapp.qtools.file.FileHelperParam;

/**
 * file AbstractFileHelper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public abstract class AbstractFileHelper implements FileHelper {

    protected FileHelperParam fileHelperParam;

    protected Base64Helper base64Helper;

    public AbstractFileHelper(FileHelperParam fileHelperParam, Base64Helper base64Helper) {
        this.fileHelperParam = fileHelperParam;
        this.base64Helper = base64Helper;
    }

    @Override
    public Base64Helper base64() {
        return this.base64Helper;
    }
}

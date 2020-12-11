package com.liumapp.qtools.file;

import com.liumapp.qtools.file.core.AbstractFileHelper;
import com.liumapp.qtools.file.core.FileHelper;

import java.io.IOException;
import java.io.Serializable;

/**
 * file AioFileHelper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/11
 */
public class AioFileHelper extends AbstractFileHelper implements FileHelper, Serializable {

    private static final long serialVersionUID = 3093981275449260130L;

    @Override
    public byte[] readyBytesByFilePath(String filePath) throws IOException {
        return new byte[0];
    }
}
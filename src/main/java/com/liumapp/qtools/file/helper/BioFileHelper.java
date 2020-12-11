package com.liumapp.qtools.file.helper;

import com.liumapp.qtools.file.FileHelperParam;
import com.liumapp.qtools.file.core.AbstractFileHelper;
import com.liumapp.qtools.file.core.FileHelper;
import com.liumapp.qtools.file.core.annotations.IOType;
import com.liumapp.qtools.file.core.enums.IOEnum;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

/**
 * file BioFileHelper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/11
 */
@IOType(IOEnum.BIO)
public class BioFileHelper extends AbstractFileHelper implements FileHelper, Serializable {

    private static final long serialVersionUID = -7187701923903043140L;

    public BioFileHelper(FileHelperParam param) {
        this.fileHelperParam = param;
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] readyBytesByFilePath(String filePath) {
        return new byte[0];
    }

    @Override
    public void writeBytesToFile(byte[] bytes, URL filePath) {

    }

}

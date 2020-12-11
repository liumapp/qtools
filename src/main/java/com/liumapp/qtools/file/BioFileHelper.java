package com.liumapp.qtools.file;

import com.liumapp.qtools.file.core.AbstractFileHelper;
import com.liumapp.qtools.file.core.FileHelper;

import java.io.IOException;
import java.io.Serializable;

public class BioFileHelper extends AbstractFileHelper implements FileHelper, Serializable {

    private static final long serialVersionUID = -7187701923903043140L;

    protected BioFileHelper(FileHelperParam param) {
        this.fileHelperParam = param;
    }

    @Override
    public byte[] readyBytesByFilePath(String filePath) throws IOException {
        return new byte[0];
    }
}

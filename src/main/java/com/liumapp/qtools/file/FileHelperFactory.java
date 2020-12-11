package com.liumapp.qtools.file;

import com.liumapp.qtools.core.AbstractFactory;
import com.liumapp.qtools.file.core.FileHelper;
import com.liumapp.qtools.file.core.enums.IOEnum;

import java.io.Serializable;
import java.util.List;

/**
 * file FileHelperFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class FileHelperFactory extends AbstractFactory<FileHelper> implements Serializable {

    private static final long serialVersionUID = 5046605863380012214L;

    private static FileHelperFactory INSTANCE;

    private FileHelperParam fileHelperParam;

    private FileHelperFactory() {
        fileHelperParam = new FileHelperParam();
    }

    public static synchronized FileHelperFactory getFactoryInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileHelperFactory();
        }
        return INSTANCE;
    }

    @Override
    public FileHelper build() {
        this.t = INSTANCE.createInstance();
        return this.t;
    }

    @Override
    public FileHelper getInstance() {
        return this.createInstanceIfNotExists();
    }

    @Override
    protected FileHelper createInstance() {
        return new SimpleFileHelper(fileHelperParam);
    }

    public FileHelperFactory setIoType (IOEnum ioType) {
        fileHelperParam.ioType = ioType;
        return this;
    }

    public FileHelperFactory setSupportTransferTo (Boolean needSupportTransferTo) {
        fileHelperParam.supportTransferTo = needSupportTransferTo;
        return this;
    }

    public FileHelperFactory setAutoCreateFolder (Boolean needAutoCreateFolder) {
        fileHelperParam.autoCreateFolder = needAutoCreateFolder;
        return this;
    }


}

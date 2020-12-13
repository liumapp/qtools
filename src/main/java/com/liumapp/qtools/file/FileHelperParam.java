package com.liumapp.qtools.file;

import com.liumapp.qtools.file.core.enums.IOEnum;

import java.io.Serializable;

/**
 * file FileHelperParam.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/11
 */
public class FileHelperParam implements Serializable {

    private static final long serialVersionUID = 1808689059161503731L;

    /**
     * io type
     */
    protected IOEnum ioType = IOEnum.NIO;

    /**
     * is need to support transferTo
     */
    protected Boolean supportTransferTo = true;

    /**
     * if folder not exists, should qtools auto create it
     */
    protected Boolean autoCreateFolder = true;

    public FileHelperParam() {
    }

    public FileHelperParam(IOEnum ioType, Boolean supportTransferTo, Boolean autoCreateFolder) {
        this.ioType = ioType;
        this.supportTransferTo = supportTransferTo;
        this.autoCreateFolder = autoCreateFolder;
    }

    public IOEnum getIoType() {
        return ioType;
    }

    public Boolean getSupportTransferTo() {
        return supportTransferTo;
    }

    public Boolean getAutoCreateFolder() {
        return autoCreateFolder;
    }
}

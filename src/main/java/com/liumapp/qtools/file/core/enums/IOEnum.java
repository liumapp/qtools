package com.liumapp.qtools.file.core.enums;

/**
 * file IOEnum.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/11
 */
public enum IOEnum {

    BIO("bio"),

    NIO("nio"),

    AIO("aio")

    ;

    IOEnum(String ioTypeName) {
        this.ioTypeName = ioTypeName;
    }

    private String ioTypeName;

    public String getIoTypeName() {
        return ioTypeName;
    }




}

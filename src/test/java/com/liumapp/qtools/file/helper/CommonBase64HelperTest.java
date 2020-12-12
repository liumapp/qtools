package com.liumapp.qtools.file.helper;

import com.liumapp.qtools.Qtools;
import com.liumapp.qtools.QtoolsFactory;
import com.liumapp.qtools.file.core.FileHelper;
import com.liumapp.qtools.file.core.enums.IOEnum;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * file CommonBase64HelperTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/12
 */
public class CommonBase64HelperTest {

    private Qtools qtools = QtoolsFactory.getFactoryInstance().getInstance();

    private FileHelper fileHelper = qtools.newFileHelperBuilder()
            .setIoType(IOEnum.NIO)
            .setSupportTransferTo(true)
            .setAutoCreateFolder(true)
            .build();

    @Test
    public void stringToBytes() {
    }

    @Test
    public void header() {

    }

    @Test
    public void removeHeader() {

    }

}
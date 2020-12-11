package com.liumapp.qtools.file;

import com.liumapp.qtools.QtoolsFactory;
import com.liumapp.qtools.file.core.FileHelper;
import com.liumapp.qtools.file.core.enums.IOEnum;
import com.liumapp.qtools.file.core.exceptions.CreateFileHelperException;
import com.liumapp.qtools.file.helper.NioFileHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * file FileHelperFactoryTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/11
 */
public class FileHelperFactoryTest {

    @Test(expected = CreateFileHelperException.class)
    public void createInstanceByIOTypeWithException () {
        FileHelper fileHelper = QtoolsFactory.getFactoryInstance().getInstance().newFileHelperBuilder()
                .setIoType(IOEnum.BIO)
                .build();
        System.out.println("捕获到不受支持的类型");
    }

    public void createInstanceByIOType () {
        FileHelper fileHelper = QtoolsFactory.getFactoryInstance().getInstance().newFileHelperBuilder()
                .setIoType(IOEnum.NIO)
                .build();
        assertTrue(fileHelper instanceof NioFileHelper);
    }



}
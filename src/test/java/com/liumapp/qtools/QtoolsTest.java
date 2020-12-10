package com.liumapp.qtools;

import junit.framework.TestCase;

import java.io.IOException;

/**
 * file QtoolsTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class QtoolsTest extends TestCase {

    /**
     * 1. 根据base64文本创建文件;
     * 2. 通过零拷贝存储文件在系统磁盘;
     * 3. 如果磁盘目录不存在，则创建;
     * 4. 如果磁盘目录地址已有同名文件，则覆盖;
     */
    public void testBase64() throws IOException {
        Qtools qtools = QtoolsFactory.getInstance();
        byte[] b = qtools.getFileHelper().readyBytesByFilePath(this.getClass().getResource("/content.txt").getPath());
        System.out.println(new String(b,0, b.length));
    }

}

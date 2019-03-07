package com.liumapp.qtools.file.zip;

import net.lingala.zip4j.exception.ZipException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * file ZipToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/3/7
 */
public class ZipToolTest {

    @Test
    public void zip() throws ZipException, IOException {
        ZipTool.zip("../data", "123");

    }

    @Test
    public void zip1() {
    }

    @Test
    public void zip2() {
    }

    @Test
    public void unzip() {
    }

    @Test
    public void unzip1() {
    }

    @Test
    public void unzip2() {
    }

}
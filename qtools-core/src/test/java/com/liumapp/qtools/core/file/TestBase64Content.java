package com.liumapp.qtools.core.file;

/**
 * file TestBase64Content.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/29
 */
public class TestBase64Content {

    private String testBase64WithHeader = "data:application/msword;base64,0M8R4KGxGuEAAAAAAAAAAAAAAA";

    private String testBase64WithoutHeader = "JVBERi0xLjUNCj==";

    public String getTestBase64WithHeader() {
        return testBase64WithHeader;
    }

    public String getTestBase64WithoutHeader() {
        return testBase64WithoutHeader;
    }
}

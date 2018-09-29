package com.liumapp.qtools.file.search;

import com.liumapp.qtools.file.config.TestConfig;
import junit.framework.TestCase;

/**
 * file SearchFileTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/29
 */
public class SearchFileTest extends TestCase {

    public void testSearchTxt () {
        SearchFileTool.getFileName(TestConfig.savePath, "test");
    }

}

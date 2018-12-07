package com.liumapp.qtools.property.core.loader;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * file AbstractConfigurationLoaderTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 */
public class AbstractConfigurationLoaderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testLoadNonexistantPath () throws IOException {
        File tmpFile = folder.newFile("text5.txt");
        Path tmpPath = Objects.requireNonNull(tmpFile, "file").toPath();
        TestConfigurationLoader loader = TestConfigurationLoader.builder().setPath(tmpPath).build();
        loader.load();
    }

    @Test
    public void testLoadNonexistantFile () throws IOException {
        File tmpFile = folder.newFile("text5.txt");
        TestConfigurationLoader loader = TestConfigurationLoader.builder().setFile(tmpFile).build();
        loader.load();
    }
}

/*
 * Configurate
 * Copyright (C) zml and Configurate contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liumapp.qtools.property.gson;

import com.google.common.io.Resources;
import com.liumapp.qtools.property.core.ConfigurationNode;
import com.liumapp.qtools.property.core.SimpleConfigurationNode;
import com.liumapp.qtools.property.core.loader.AtomicFiles;
import com.liumapp.qtools.property.core.loader.ConfigurationLoader;
import com.liumapp.qtools.property.core.util.MapFactories;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * file GsonConfigurationLoaderTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 * Basic sanity checks for the loader
 */
public class GsonConfigurationLoaderTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testSimpleLoading() throws IOException {
        URL url = getClass().getResource("/example.json");
        final File tmpFile = temporaryFolder.newFile("text1.txt");
        final Path tmpPath = Objects.requireNonNull(tmpFile, "file").toPath();
        ConfigurationLoader<ConfigurationNode> loader = GsonConfigurationLoader.builder()
                .setSource(() -> new BufferedReader(new InputStreamReader(url.openStream())))
                .setSink(AtomicFiles.createAtomicWriterFactory(tmpPath, UTF_8)).setLenient(true).build();
        ConfigurationNode node = loader.load(loader.getDefaultOptions().setMapFactory(MapFactories.sortedNatural()));
        Assert.assertEquals("unicorn", node.getNode("test", "op-level").getValue());
        Assert.assertEquals("dragon", node.getNode("other", "op-level").getValue());
        Assert.assertEquals("dog park", node.getNode("other", "location").getValue());
        Assert.assertTrue(node.getNode("int-val").getValue() instanceof Integer);
        Assert.assertTrue(node.getNode("double-val").getValue() instanceof Double);
        loader.save(node);
        Assert.assertEquals(Resources.readLines(url, UTF_8), Files.readAllLines(tmpPath, UTF_8));
    }

    @Test
    public void testSavingEmptyFile() throws IOException {
        final File tmpFile = temporaryFolder.newFile("text2.txt");

        ConfigurationLoader<ConfigurationNode> loader = GsonConfigurationLoader.builder()
                .setFile(tmpFile)
                .build();

        ConfigurationNode n = SimpleConfigurationNode.root();
        loader.save(n);
    }

    @Test
    public void testLoadingEmptyFile() throws IOException {
        final File tmpFile = temporaryFolder.newFile("text3.txt");

        ConfigurationLoader<ConfigurationNode> loader = GsonConfigurationLoader.builder()
                .setFile(tmpFile)
                .build();

        loader.load();
    }

    @Test
    public void testLoadingFileWithEmptyObject() throws IOException {
        URL url = getClass().getResource("/emptyObject.json");
        final File tmpFile = temporaryFolder.newFile("text4.txt");
        final Path tmpPath = Objects.requireNonNull(tmpFile, "file").toPath();
        ConfigurationLoader<ConfigurationNode> loader = GsonConfigurationLoader.builder()
                .setSource(() -> new BufferedReader(new InputStreamReader(url.openStream())))
                .setSink(AtomicFiles.createAtomicWriterFactory(tmpPath, UTF_8)).setLenient(true).build();

        ConfigurationNode node = loader.load(loader.getDefaultOptions().setMapFactory(MapFactories.sortedNatural()));
        Assert.assertNull(node.getValue());
        Assert.assertFalse(node.hasMapChildren());
    }

    private static final long TEST_LONG_VAL = 584895858588588888l;

    /**
     * "Gson currently makes it rather difficult to get the correct number type"
     */
    @Test
    public void testRoundtrippingLong() throws IOException {
        final File tmpFile = temporaryFolder.newFile("text5.txt");
        final Path tmpPath = Objects.requireNonNull(tmpFile, "file").toPath();
        ConfigurationLoader<ConfigurationNode> loader = GsonConfigurationLoader.builder().setPath(tmpPath).build();
        ConfigurationNode start = loader.createEmptyNode();
        start.getNode("long-num").setValue(TEST_LONG_VAL);
        loader.save(start);
        System.out.println(Files.readAllLines(tmpPath));

        ConfigurationNode ret = loader.load();
        System.out.println(ret.getNode("long-num").getValue().getClass());
        Assert.assertEquals(TEST_LONG_VAL, ret.getNode("long-num").getValue());
    }
}

package com.liumapp.qtools.property.json;

import com.google.common.io.Resources;
import com.liumapp.qtools.property.core.ConfigurationNode;
import com.liumapp.qtools.property.core.ConfigurationOptions;
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
 * file JSONConfigurationLoaderTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 * Basic sanity checks for the loader
 */
public class JSONConfigurationLoaderTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testSimpleLoading() throws IOException {
        URL url = getClass().getResource("/json/example.json");
        final File tmpFile = temporaryFolder.newFile("text1.txt");
        final Path tmpPath = Objects.requireNonNull(tmpFile, "file").toPath();
        ConfigurationLoader loader = JSONConfigurationLoader.builder()
                .setSource(() -> new BufferedReader(new InputStreamReader(url.openStream(), UTF_8)))
                        .setSink(AtomicFiles.createAtomicWriterFactory(tmpPath, UTF_8)).build();
        ConfigurationNode node = loader.load(ConfigurationOptions.defaults().setMapFactory(MapFactories.sortedNatural()));
        Assert.assertEquals("unicorn", node.getNode("test", "op-level").getValue());
        Assert.assertEquals("dragon", node.getNode("other", "op-level").getValue());
        Assert.assertEquals("dog park", node.getNode("other", "location").getValue());
        /*CommentedConfigurationNode commentNode = SimpleCommentedConfigurationNode.root();
        commentNode.getNode("childOne").setValue("a").setComment("Test comment");
        commentNode.getNode("childTwo", "something").setValue("b").setComment("Test comment 2");
        commentNode.getNode("childTwo", "another").setValue("b").setComment("Test comment 3");
        */
        loader.save(node);
        Assert.assertEquals(Resources.readLines(url, UTF_8), Files
                .readAllLines(tmpPath, UTF_8));

    }

    private static final long TEST_LONG_VAL = 584895858588588888l;
    private static final double TEST_DOUBLE_VAL = 595859682984428959583045732020572045273498409257349587.85485884287387d;

    @Test
    public void testRoundtrippingLong() throws IOException {
        testRoundtripValue(TEST_LONG_VAL);
    }

    @Test
    public void testRoundtripDouble() throws IOException {
        testRoundtripValue(TEST_DOUBLE_VAL);
    }

    private void testRoundtripValue(Object value) throws IOException {
        final File tmpFile = temporaryFolder.newFile("text2.txt");
        final Path tmpPath = Objects.requireNonNull(tmpFile, "file").toPath();
        ConfigurationLoader<ConfigurationNode> loader = JSONConfigurationLoader.builder().setPath(tmpPath).build();
        ConfigurationNode start = loader.createEmptyNode();
        start.getNode("value").setValue(value);
        loader.save(start);

        ConfigurationNode ret = loader.load();
        Assert.assertEquals(value, ret.getNode("value").getValue());
    }

}

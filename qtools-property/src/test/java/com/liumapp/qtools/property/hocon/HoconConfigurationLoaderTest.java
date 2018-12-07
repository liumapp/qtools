package com.liumapp.qtools.property.hocon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import com.liumapp.qtools.property.core.ConfigurationOptions;
import com.liumapp.qtools.property.core.commented.CommentedConfigurationNode;
import com.liumapp.qtools.property.core.loader.AtomicFiles;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Basic sanity checks for the loader
 */
public class HoconConfigurationLoaderTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testSimpleLoading() throws IOException {
        URL url = getClass().getResource("/hocon/example.conf");
        final File tmpFile = temporaryFolder.newFile("text1.txt");
        final Path savePath = Objects.requireNonNull(tmpFile, "file").toPath();

        HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .setSource(() -> new BufferedReader(new InputStreamReader(url.openStream(), UTF_8)))
                .setSink(AtomicFiles.createAtomicWriterFactory(savePath, UTF_8)).build();
        CommentedConfigurationNode node = loader.load();
        Assert.assertEquals("unicorn", node.getNode("test", "op-level").getValue());
        Assert.assertEquals("dragon", node.getNode("other", "op-level").getValue());
        CommentedConfigurationNode testNode = node.getNode("test");
        Assert.assertEquals(" Test node", testNode.getComment().orElse(null));
        Assert.assertEquals("dog park", node.getNode("other", "location").getValue());
        loader.save(node);
        Assert.assertEquals(Resources.readLines(getClass().getResource("/hocon/roundtrip-test.conf"), UTF_8), Files
                .readAllLines(savePath, UTF_8));
    }

    @Test
    public void testSplitLineCommentInput() throws IOException {
        final File tmpFile = temporaryFolder.newFile("text2.txt");
        final Path savePath = Objects.requireNonNull(tmpFile, "file").toPath();
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .setPath(savePath)
                .setURL(getClass().getResource("/hocon/splitline-comment-input.conf"))
                .build();
        CommentedConfigurationNode node = loader.load();
        System.out.println(node.getOptions().getHeader());
        loader.save(node);

        Assert.assertEquals(Resources.readLines(getClass().getResource("/hocon/splitline-comment-output.conf"), UTF_8), Files.readAllLines(savePath, UTF_8));
    }

    @Test
    public void testHeaderSaved() throws IOException {
        final File tmpFile = temporaryFolder.newFile("text3.txt");
        final Path savePath = Objects.requireNonNull(tmpFile, "file").toPath();
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .setPath(savePath)
                .build();
        CommentedConfigurationNode node = loader.createEmptyNode(ConfigurationOptions.defaults().setHeader("Hi! I am a header!\n" +
                        "Look at meeeeeee!!!"));
        node.getNode("node").setComment("I have a comment").getNode("party").setValue("now");

        loader.save(node);
        Assert.assertEquals(Resources.readLines(getClass().getResource("/hocon/header.conf"), UTF_8), Files.readAllLines(savePath, UTF_8));
    }

    @Test
    public void testBooleansNotShared() throws IOException {
        URL url = getClass().getResource("/hocon/comments-test.conf");
        final File tmpFile = temporaryFolder.newFile("text4.txt");
        final Path savePath = Objects.requireNonNull(tmpFile, "file").toPath();
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .setPath(savePath).setURL(url).build();

        CommentedConfigurationNode node = loader.createEmptyNode(ConfigurationOptions.defaults());
        node.getNode("test", "third").setValue(false).setComment("really?");
        node.getNode("test", "apple").setComment("fruit").setValue(false);
        node.getNode("test", "donut").setValue(true).setComment("tasty");
        node.getNode("test", "guacamole").setValue(true).setComment("and chips?");

        loader.save(node);
        Assert.assertEquals(Resources.readLines(url, UTF_8), Files.readAllLines(savePath, UTF_8));
    }

    @Test
    public void testNewConfigObject() {
        Map<String, ConfigValue> entries = ImmutableMap.of("a", ConfigValueFactory.fromAnyRef("hi"), "b", ConfigValueFactory.fromAnyRef("bye"));
        HoconConfigurationLoader.newConfigObject(entries);
    }

    @Test
    public void testNewConfigList() {
        List<ConfigValue> entries = ImmutableList.of(ConfigValueFactory.fromAnyRef("hello"), ConfigValueFactory.fromAnyRef("goodbye"));
        HoconConfigurationLoader.newConfigList(entries);
    }
}

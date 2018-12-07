package com.liumapp.qtools.property.xml;

import com.google.common.io.Resources;
import com.liumapp.qtools.property.core.attributed.AttributedConfigurationNode;
import com.liumapp.qtools.property.core.loader.AtomicFiles;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * file XMLConfigurationLoaderTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 * Basic sanity checks for the loader
 */
public class XMLConfigurationLoaderTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testSimpleLoading() throws IOException {
        URL url = getClass().getResource("/xml/example.xml");
        final File tmpFile = temporaryFolder.newFile("text1.txt");
        final Path tmpPath = Objects.requireNonNull(tmpFile, "file").toPath();

        XMLConfigurationLoader loader = XMLConfigurationLoader.builder()
                .setWriteExplicitType(false)
                .setIndent(4)
                .setSource(() -> new BufferedReader(new InputStreamReader(url.openStream(), UTF_8)))
                .setSink(AtomicFiles.createAtomicWriterFactory(tmpPath, UTF_8)).build();

        AttributedConfigurationNode node = loader.load();

        assertEquals("messages", node.getTagName());
        assertEquals("false", node.getAttribute("secret"));
        assertTrue(node.hasListChildren());

        List<? extends AttributedConfigurationNode> notes = node.getChildrenList();
        assertEquals(2, notes.size());

        AttributedConfigurationNode firstNote = notes.get(0);
        assertEquals("501", firstNote.getAttribute("id"));
        assertTrue(firstNote.hasMapChildren());
        assertFalse(firstNote.hasListChildren());

        Map<Object, ? extends AttributedConfigurationNode> properties = firstNote.getChildrenMap();
        assertEquals("Tove", properties.get("to").getValue());
        assertEquals("Jani", properties.get("from").getValue());
        assertEquals("Don't forget me this weekend!", properties.get("body").getValue());
        assertEquals("heading", properties.get("heading").getTagName());

        AttributedConfigurationNode secondNode = notes.get(1);
        assertEquals("502", secondNode.getAttribute("id"));
        assertFalse(secondNode.hasMapChildren());
        assertTrue(secondNode.hasListChildren());

        List<? extends AttributedConfigurationNode> subNodes = secondNode.getChildrenList();
        for (AttributedConfigurationNode subNode : subNodes) {
            if (subNode.getTagName().equals("heading")) {
                assertEquals("true", subNode.getAttribute("bold"));
            }
        }

        // roundtrip!
        loader.save(node);
        assertEquals(Resources.readLines(url, UTF_8), Files.readAllLines(tmpPath));
    }

    @Test
    public void testExplicitTypes() throws IOException {
        URL url = getClass().getResource("/xml/example2.xml");
        final File tmpFile = temporaryFolder.newFile("text2.txt");
        final Path savePath = Objects.requireNonNull(tmpFile, "file").toPath();

        XMLConfigurationLoader loader = XMLConfigurationLoader.builder()
                .setWriteExplicitType(true)
                .setIncludeXmlDeclaration(false)
                .setIndent(4)
                .setSource(() -> new BufferedReader(new InputStreamReader(url.openStream(), UTF_8)))
                .setSink(AtomicFiles.createAtomicWriterFactory(savePath, UTF_8)).build();

        AttributedConfigurationNode node = loader.load();

        AttributedConfigurationNode list1 = node.getNode("list1");
        assertTrue(list1.hasListChildren());

        AttributedConfigurationNode list2 = node.getNode("list2");
        assertTrue(list2.hasListChildren());

        AttributedConfigurationNode map1 = node.getNode("map1");
        assertTrue(map1.hasMapChildren());

        AttributedConfigurationNode map2 = node.getNode("map2");
        assertTrue(map2.hasMapChildren());

        // roundtrip!
        loader.save(node);
        assertEquals(Resources.readLines(url, UTF_8), Files.readAllLines(savePath));
    }

    @Test
    public void testComments() throws IOException {
        URL url = getClass().getResource("/xml/example3.xml");
        final File tmpFile = temporaryFolder.newFile("text3.txt");
        final Path savePath = Objects.requireNonNull(tmpFile, "file").toPath();

        XMLConfigurationLoader loader = XMLConfigurationLoader.builder()
                .setWriteExplicitType(true)
                .setIncludeXmlDeclaration(true)
                .setIndent(4)
                .setSource(() -> new BufferedReader(new InputStreamReader(url.openStream(), UTF_8)))
                .setSink(AtomicFiles.createAtomicWriterFactory(savePath, UTF_8)).build();

        AttributedConfigurationNode node = loader.createEmptyNode(
                loader.getDefaultOptions().setHeader("test header\ndo multiple lines work\nyes they do!!")
        );

        node.setValue("something").setTagName("test");

        loader.save(node);
        assertEquals(Resources.readLines(url, UTF_8), Files.readAllLines(savePath));
    }
}

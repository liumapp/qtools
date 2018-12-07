package com.liumapp.qtools.property.core.loader;

import com.google.common.base.Joiner;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;

/**
 * file CommentHandlersTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 */
public class CommentHandlersTest {

    @Test
    public void testExtractBlockCommentHeader() throws IOException {
        final String testDocument = "/*\n" +
                " * First header line\n" +
                " * more header\n" +
                " * even more header\n" +
                " */";

        Optional<String> head = CommentHandlers.SLASH_BLOCK.extractHeader(new BufferedReader(new StringReader(testDocument)));
        Assert.assertTrue(head.isPresent());
        Assert.assertEquals("First header line\n" +
                "more header\n" +
                "even more header", head.get());

        Assert.assertEquals(testDocument, Joiner.on('\n').join(CommentHandlers.SLASH_BLOCK.toComment(AbstractConfigurationLoader
                .LINE_SPLITTER
                .splitToList(head.get()))));

    }

    @Test
    public void testExtractSingleLineBlockComment() throws IOException {
        final String testDocument = "/* single line */\n";
        try (BufferedReader read = new BufferedReader(new StringReader(testDocument))) {
            Optional<String> head = CommentHandlers.SLASH_BLOCK.extractHeader(read);
            Assert.assertTrue(head.isPresent());
            Assert.assertEquals("single line", head.get());
        }
    }

    @Test
    public void testExtractLineCommentHeader() throws IOException {
        final String testDocument = "# First header line\n" +
                "# more header\n" +
                "# even more header";

        Optional<String> head = CommentHandlers.HASH.extractHeader(new BufferedReader(new StringReader(testDocument)));
        Assert.assertTrue(head.isPresent());
        Assert.assertEquals("First header line\n" +
                "more header\n" +
                "even more header", head.get());

    }
}

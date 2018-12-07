package com.liumapp.qtools.property.core;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;

/**
 * file CopyTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 */
public class CopyTest {

    @Test
    public void testSimpleCopy() {
        ConfigurationNode node = SimpleConfigurationNode.root();
        node.getNode("test").setValue(5);
        node.getNode("section", "val1").setValue(true);
        node.getNode("section", "val2").setValue("TEST");
        ConfigurationNode list = node.getNode("section2", "alist");
        list.getAppendedNode().setValue("value1");
        list.getAppendedNode().setValue("value2");

        ConfigurationNode copy = node.copy();

        Assert.assertNotSame(node, copy);
        Assert.assertEquals(node, copy);

        Assert.assertFalse(node.isVirtual());
        Assert.assertFalse(copy.isVirtual());

        Assert.assertEquals(5, copy.getNode("test").getValue());
        Assert.assertEquals(true, copy.getNode("section", "val1").getValue());
        Assert.assertEquals("TEST", copy.getNode("section", "val2").getValue());
        Assert.assertEquals(ImmutableList.of("value1", "value2"), copy.getNode("section2", "alist").getValue());

        // change value on original
        node.getNode("section", "val2").setValue("NOT TEST");

        // test it's still the same on copy
        Assert.assertEquals("TEST", copy.getNode("section", "val2").getValue());

        // change value on copy
        copy.getNode("section", "val2").setValue("zzz");

        // test it's still the same on original
        Assert.assertEquals("NOT TEST", node.getNode("section", "val2").getValue());
    }

    @Test
    public void testCopyPaths() {
        ConfigurationNode node = SimpleConfigurationNode.root();
        node.getNode("test").setValue(5);
        node.getNode("section", "val1").setValue(true);
        node.getNode("section", "val2").setValue("TEST");

        ConfigurationNode original = node.getNode("section");
        ConfigurationNode copy = original.copy();

        Assert.assertNotNull(original.getParent());
        Assert.assertNull(copy.getParent());

        ConfigurationNode originalVal = original.getNode("val1");
        ConfigurationNode copyVal = copy.getNode("val1");

        Assert.assertEquals(2, originalVal.getPath().length);
        Assert.assertEquals(1, copyVal.getPath().length);

        Assert.assertNotNull(originalVal.getParent());
        Assert.assertNotNull(copyVal.getParent());
        Assert.assertNotSame(originalVal.getParent(), copyVal.getParent());
    }

}

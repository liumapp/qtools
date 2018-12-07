package com.liumapp.qtools.property.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import com.liumapp.qtools.property.core.objectmapping.ObjectMappingException;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * file SimpleConfigurationNodeTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 */
public class SimpleConfigurationNodeTest {

    @Test
    public void testUnattachedNodesTemporary() {
        ConfigurationNode config = SimpleConfigurationNode.root();
        ConfigurationNode node = config.getNode("some", "node");
        Assert.assertEquals(true, node.isVirtual());
        Assert.assertEquals(null, node.getValue());
        Assert.assertEquals(false, node.hasListChildren());
        Assert.assertEquals(false, node.hasMapChildren());
        ConfigurationNode node2 = config.getNode("some", "node");
        Assert.assertNotSame(node, node2);


        ConfigurationNode node3 = config.getNode("some").getNode("node");
        Assert.assertNotSame(node, node3);
    }

    @Test
    public void testNodeCreation() {
        ConfigurationNode config = SimpleConfigurationNode.root();
        ConfigurationNode uncreatedNode = config.getNode("uncreated", "node");
        Assert.assertTrue(uncreatedNode.isVirtual()); // Just in case
        uncreatedNode.setValue("test string for cool people");
        Assert.assertFalse(uncreatedNode.isVirtual());
        Assert.assertEquals("test string for cool people", uncreatedNode.getValue());

        ConfigurationNode fetchedAfterCreation = config.getNode("uncreated", "node");
        Assert.assertEquals(uncreatedNode, fetchedAfterCreation);
        Assert.assertEquals(uncreatedNode, config.getNode("uncreated").getNode("node"));
    }

    @Test
    public void testTraversingNodeCreation() {
        SimpleConfigurationNode config = SimpleConfigurationNode.root();
        SimpleConfigurationNode nodeOne = config.getNode("uncreated", "step", "node");
        SimpleConfigurationNode nodeTwo = config.getNode("uncreated", "step", "color");
        nodeOne.setValue("one");
        nodeTwo.setValue("lilac");
        ConfigurationNode attachedParent = config.getNode("uncreated", "step");
        Assert.assertEquals(attachedParent, nodeOne.getParentEnsureAttached());
        Assert.assertEquals(attachedParent, nodeTwo.getParentEnsureAttached());
    }

    @Test
    public void testGetDefaultValue() {
        ConfigurationNode root = SimpleConfigurationNode.root();
        final Object testObj = new Object();
        Assert.assertEquals(testObj, root.getNode("nonexistent").getValue(testObj));
    }

    @Test
    public void testGetChildrenMap() {
        ConfigurationNode root = SimpleConfigurationNode.root();
        ConfigurationNode a = root.getNode("a").setValue("one");
        ConfigurationNode b = root.getNode("b").setValue("two");
        Assert.assertEquals(ImmutableMap.<Object, ConfigurationNode>of("a", a, "b", b), root.getChildrenMap());
    }

    @Test
    public void testGetChildrenList() {
        ConfigurationNode root = SimpleConfigurationNode.root();
        ConfigurationNode a = root.getAppendedNode().setValue("one");
        ConfigurationNode b = root.getAppendedNode().setValue("two");
        Assert.assertEquals(ImmutableList.of(a, b), root.getChildrenList());
    }

    private static final Map<Object, Object> TEST_MAP = new HashMap<>();
    private static final List<Object> TEST_LIST = new ArrayList<>();
    static {
        TEST_LIST.add("test1");
        TEST_LIST.add("test2");

        TEST_MAP.put("key", "value");
        TEST_MAP.put("fabulous", true);
    }
    @Test
    public void testMapUnpacking() {
        ConfigurationNode root = SimpleConfigurationNode.root();
        root.setValue(TEST_MAP);
        Assert.assertEquals("value", root.getNode("key").getValue());
        Assert.assertEquals(true, root.getNode("fabulous").getValue());
    }

    @Test
    public void testMapPacking() {
        ConfigurationNode root = SimpleConfigurationNode.root();
        root.getNode("key").setValue("value");
        root.getNode("fabulous").setValue(true);

        Assert.assertEquals(TEST_MAP, root.getValue());
    }

    @Test
    public void testListUnpacking() {
        ConfigurationNode root = SimpleConfigurationNode.root();
        root.setValue(TEST_LIST);
        Assert.assertEquals("test1", root.getNode(0).getValue());
        Assert.assertEquals("test2", root.getNode(1).getValue());
    }

    @Test
    public void testListPacking() {
        ConfigurationNode root = SimpleConfigurationNode.root();
        root.getAppendedNode().setValue("test1");
        root.getAppendedNode().setValue("test2");
        Assert.assertEquals(TEST_LIST, root.getValue());
    }

    @Test
    public void testSingleListConversion() {
        ConfigurationNode config = SimpleConfigurationNode.root();
        ConfigurationNode node = config.getNode("test", "value");
        node.setValue("test");
        ConfigurationNode secondChild = node.getAppendedNode();
        secondChild.setValue("test2");
        Assert.assertEquals(Arrays.asList("test", "test2"), node.getValue());
    }

    @Test
    public void testSettingNullRemoves() {
        ConfigurationNode root = SimpleConfigurationNode.root();
        ConfigurationNode child = root.getNode("child").setValue("a");
        Assert.assertFalse(child.isVirtual());
        Assert.assertSame(child, root.getNode("child"));
        child.setValue(null);
        Assert.assertTrue(child.isVirtual());
        Assert.assertNotSame(child, root.getNode("child"));
    }

    @Test
    public void testGetPath() {
        ConfigurationNode root = SimpleConfigurationNode.root();
        Assert.assertArrayEquals(new Object[]{"a", "b", "c"}, root.getNode("a", "b", "c").getPath());
    }

    @Test
    public void testMergeValues() {
        SimpleConfigurationNode first = SimpleConfigurationNode.root();
        SimpleConfigurationNode second = SimpleConfigurationNode.root();
        first.getNode("scalar").setValue("one");
        first.getNode("absent").setValue("butmerged");
        second.getNode("scalar").setValue("two");

        ConfigurationNode firstAbsentMap = first.getNode("absent-map");
        firstAbsentMap.getNode("a").setValue("one");
        firstAbsentMap.getNode("b").setValue("two");

        ConfigurationNode firstMergedMap = first.getNode("merged-map");
        ConfigurationNode secondMergedMap = second.getNode("merged-map");
        firstMergedMap.getNode("source").setValue("first");
        secondMergedMap.getNode("source").setValue("second");
        firstMergedMap.getNode("first-only").setValue("yeah");
        secondMergedMap.getNode("second-only").setValue("yeah");

        second.mergeValuesFrom(first);
        Assert.assertEquals("two", second.getNode("scalar").getString());
        Assert.assertEquals("butmerged", second.getNode("absent").getString());
        Assert.assertEquals("one", second.getNode("absent-map", "a").getString());
        Assert.assertEquals("two", second.getNode("absent-map", "b").getString());
        Assert.assertEquals("second", second.getNode("merged-map", "source").getString());
        Assert.assertEquals("yeah", second.getNode("merged-map", "first-only").getString());
        Assert.assertEquals("yeah", second.getNode("merged-map", "second-only").getString());
    }

    @Test
    public void testSettingMultipleTimesWorks() {
        SimpleConfigurationNode subject = SimpleConfigurationNode.root();
        subject.setValue(ImmutableMap.of("a", "b", "b", "c", "c", "d"));
        Assert.assertTrue(subject.hasMapChildren());
        subject.setValue(ImmutableMap.of("na", "na", "eh", "eh", "bleugh", "bleugh"));
        Assert.assertTrue(subject.hasMapChildren());
    }

    @Test
    public void testGetSetValueSerialized() throws ObjectMappingException {
        SimpleConfigurationNode subject = SimpleConfigurationNode.root();
        subject.setValue("48");
        Assert.assertEquals((Object) 48, subject.getValue(TypeToken.of(Integer.class)));
        UUID testId = UUID.randomUUID();
        subject.setValue(TypeToken.of(UUID.class), testId);
        Assert.assertEquals(testId.toString(), subject.getValue());
    }

    @Test
    public void testDefaultsCopied() {
        SimpleConfigurationNode subject = SimpleConfigurationNode.root(ConfigurationOptions.defaults().setShouldCopyDefaults(true));
        Assert.assertNull(subject.getValue());
        Assert.assertEquals("default value", subject.getValue("default value"));
        Assert.assertEquals("default value", subject.getValue());
    }

}

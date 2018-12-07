package com.liumapp.qtools.property.core.objectmapping;

import com.liumapp.qtools.property.core.ConfigurationNode;
import com.liumapp.qtools.property.core.SimpleConfigurationNode;
import com.liumapp.qtools.property.core.commented.CommentedConfigurationNode;
import com.liumapp.qtools.property.core.commented.SimpleCommentedConfigurationNode;
import com.liumapp.qtools.property.core.objectmapping.serialize.ConfigSerializable;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * file ObjectMapperTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 */
public class ObjectMapperTest {

    @ConfigSerializable
    private static class TestObject {
        @Setting("test-key") protected String stringVal;
    }

    @Test
    public void testCreateFromNode() throws ObjectMappingException {
        final ObjectMapper<TestObject> mapper = ObjectMapper.forClass(TestObject.class);
        final ConfigurationNode source = SimpleConfigurationNode.root();
        source.getNode("test-key").setValue("some are born great, some achieve greatness, and some have greatness thrust upon them");

        final TestObject obj = mapper.bindToNew().populate(source);
        Assert.assertEquals("some are born great, some achieve greatness, and some have greatness thrust upon them", obj.stringVal);
    }

    @Test
    public void testNullsPreserved() throws ObjectMappingException {
        final ObjectMapper<TestObject> mapper = ObjectMapper.forClass(TestObject.class);
        final TestObject obj = mapper.bindToNew().populate(SimpleConfigurationNode.root());
        Assert.assertNull(obj.stringVal);
    }

    @Test
    public void testLoadExistingObject() throws ObjectMappingException {
        final ObjectMapper<TestObject> mapper = ObjectMapper.forClass(TestObject.class);
        final ConfigurationNode source = SimpleConfigurationNode.root();
        final TestObject instance = new TestObject();

        source.getNode("test-key").setValue("boom");

        mapper.bind(instance).populate(source);
        Assert.assertEquals("boom", instance.stringVal);
    }

    @Test
    public void testDefaultsApplied() throws ObjectMappingException {
        final ObjectMapper<TestObject> mapper = ObjectMapper.forClass(TestObject.class);
        final ConfigurationNode source = SimpleConfigurationNode.root();
        final TestObject instance = new TestObject();

        instance.stringVal = "hi";
        mapper.bind(instance).populate(source);
        Assert.assertEquals("hi", source.getNode("test-key").getString());
    }

    @ConfigSerializable
    private static class CommentedObject {
        @Setting(value = "commented-key", comment = "You look nice today") private String color;
        @Setting("no-comment") private String politician;
    }

    @Test
    public void testCommentsApplied() throws ObjectMappingException {
        CommentedConfigurationNode node = SimpleCommentedConfigurationNode.root();
        ObjectMapper<CommentedObject>.BoundInstance mapper = ObjectMapper.forClass(CommentedObject.class).bindToNew();
        CommentedObject obj = mapper.populate(node);
        obj.color = "fuchsia";
        obj.politician = "All of them";
        mapper.serialize(node);
        Assert.assertEquals("You look nice today", node.getNode("commented-key").getComment().orElse(null));
        Assert.assertEquals("fuchsia", node.getNode("commented-key").getString());
        Assert.assertFalse(node.getNode("no-comment").getComment().isPresent());
    }


    @ConfigSerializable
    private static class NonZeroArgConstructorObject {
        @Setting private long key;
        private final String value;

        protected NonZeroArgConstructorObject(String value) {
            this.value = value;
        }
    }

    @Test(expected = ObjectMappingException.class)
    public void testNoArglessConstructor() throws ObjectMappingException {
        ObjectMapper<NonZeroArgConstructorObject> mapper = ObjectMapper.forClass(NonZeroArgConstructorObject.class);
        Assert.assertFalse(mapper.canCreateInstances());
        mapper.bindToNew();
    }

    @ConfigSerializable
    private static class TestObjectChild extends TestObject {
        @Setting("child-setting") private boolean childSetting;
    }

    @Test
    public void testSuperclassFieldsIncluded() throws ObjectMappingException {
        final ObjectMapper<TestObjectChild> mapper = ObjectMapper.forClass(TestObjectChild.class);
        ConfigurationNode node = SimpleConfigurationNode.root();
        node.getNode("child-setting").setValue(true);
        node.getNode("test-key").setValue("Parents get populated too!");

        TestObjectChild instance = mapper.bindToNew().populate(node);
        Assert.assertEquals(true, instance.childSetting);
        Assert.assertEquals("Parents get populated too!", instance.stringVal);
    }

    @ConfigSerializable
    private static class FieldNameObject {
        @Setting private boolean loads;
    }

    @Test
    public void testKeyFromFieldName() throws ObjectMappingException {
        final ObjectMapper<FieldNameObject> mapper = ObjectMapper.forClass(FieldNameObject.class);
        final ConfigurationNode node = SimpleConfigurationNode.root();
        node.getNode("loads").setValue(true);

        FieldNameObject obj = mapper.bindToNew().populate(node);
        Assert.assertTrue(obj.loads);
    }

    private static class ParentObject {
        @Setting(comment = "Comment on parent") private InnerObject inner = new InnerObject();
    }

    @ConfigSerializable
    private static class InnerObject {
        @Setting(comment = "Something") private String test = "Default value";
    }

    @Test
    public void testNestedObjectWithComments() throws ObjectMappingException {
        CommentedConfigurationNode node = SimpleCommentedConfigurationNode.root();
        final ObjectMapper<ParentObject>.BoundInstance mapper = ObjectMapper.forObject(new ParentObject());
        mapper.populate(node);
        Assert.assertEquals("Comment on parent", node.getNode("inner").getComment().get());
        Assert.assertTrue(node.getNode("inner").hasMapChildren());
        Assert.assertEquals("Default value", node.getNode("inner", "test").getString());
        Assert.assertEquals("Something", node.getNode("inner", "test").getComment().get());
    }

    @ConfigSerializable
    private interface ParentInterface {
        String getTest();
    }

    private static class ChildObject implements ParentInterface {
        @Setting(comment = "Something") private String test = "Default value";

        @Override public String getTest() {
            return test;
        }
    }

    @ConfigSerializable
    private static class ContainingObject {
        @Setting ParentInterface inner = new ChildObject();
        @Setting List<ParentInterface> list = new ArrayList<>();
    }

    @Test
    public void testInterfaceSerialization() throws ObjectMappingException {
        CommentedConfigurationNode node = SimpleCommentedConfigurationNode.root();

        final ChildObject childObject = new ChildObject();
        childObject.test = "Changed value";

        final ContainingObject containingObject = new ContainingObject();
        containingObject.list.add(childObject);
        containingObject.inner = childObject;

        final ObjectMapper<ContainingObject> mapper = ObjectMapper.forClass(ContainingObject.class);
        mapper.bind(containingObject).serialize(node);

        final ContainingObject newContainingObject = mapper.bindToNew().populate(node);

        // serialization
        Assert.assertEquals(1, node.getNode("list").getChildrenList().size());
        Assert.assertEquals("Changed value", node.getNode("inner").getNode("test").getString());
        Assert.assertEquals("Changed value", node.getNode("list").getChildrenList().get(0).getNode("test").getString());
        Assert.assertEquals("Something", node.getNode("inner").getNode("test").getComment().get());
        Assert.assertEquals("Something", node.getNode("list").getChildrenList().get(0).getNode("test").getComment().get());
        Assert.assertEquals(ChildObject.class.getName(), node.getNode("inner").getNode("__class__").getString());
        Assert.assertEquals(ChildObject.class.getName(), node.getNode("list").getChildrenList().get(0).getNode("__class__").getString());

        // deserialization
        Assert.assertEquals(1, newContainingObject.list.size());
        Assert.assertEquals("Changed value", newContainingObject.inner.getTest());
        Assert.assertEquals("Changed value", newContainingObject.list.get(0).getTest());
    }
}

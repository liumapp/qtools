package com.liumapp.qtools.property.core.commented;

import org.junit.Assert;
import org.junit.Test;

/**
 * file SimpleCommentedConfigurationNodeTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 */
public class SimpleCommentedConfigurationNodeTest {

    @Test
    public void testCommentsTransferred() {
        CommentedConfigurationNode subject = SimpleCommentedConfigurationNode.root();
        CommentedConfigurationNode firstChild = subject.getNode("first");
        firstChild.setValue("test value");
        firstChild.setComment("Such comment. Very wow.");


        CommentedConfigurationNode secondChild = subject.getNode("second");
        secondChild.setValue("test value's evil twin");

        Assert.assertFalse(secondChild.isVirtual());

        secondChild.setValue(firstChild);
        Assert.assertEquals("test value", secondChild.getValue());
        Assert.assertEquals("Such comment. Very wow.", secondChild.getComment().orElse(null));
    }

    @Test
    public void testNestedCommentsTransferred() {
        CommentedConfigurationNode subject = SimpleCommentedConfigurationNode.root();
        CommentedConfigurationNode firstChild = subject.getNode("first");
        CommentedConfigurationNode firstChildChild = firstChild.getNode("child");
        firstChildChild.setValue("test value");
        firstChildChild.setComment("Such comment. Very wow.");


        CommentedConfigurationNode secondChild = subject.getNode("second");
        secondChild.setValue("test value's evil twin");

        Assert.assertFalse(secondChild.isVirtual());

        secondChild.setValue(firstChild);
        Assert.assertEquals("test value", secondChild.getNode("child").getValue());
        Assert.assertEquals("Such comment. Very wow.", secondChild.getNode("child").getComment().orElse(null));
    }

    @Test
    public void testCommentsMerged() {
        CommentedConfigurationNode source = SimpleCommentedConfigurationNode.root();
        CommentedConfigurationNode target = SimpleCommentedConfigurationNode.root();

        source.getNode("no-value").setValue("a").setComment("yeah");
        source.getNode("existing-value-no-comment").setValue("orig").setComment("maybe");
        source.getNode("existing-value").setValue("a").setComment("yeah");
        source.getNode("no-parent", "child").setValue("x").setComment("always");
        target.getNode("existing-value-no-comment").setValue("new");
        target.getNode("existing-value").setValue("b").setComment("nope");

        target.mergeValuesFrom(source);
        Assert.assertEquals("yeah", target.getNode("no-value").getComment().orElse(null));
        Assert.assertEquals("maybe", target.getNode("existing-value-no-comment").getComment().orElse(null));
        Assert.assertEquals("new", target.getNode("existing-value-no-comment").getString());
        Assert.assertEquals("nope", target.getNode("existing-value").getComment().orElse(null));
        Assert.assertEquals("always", target.getNode("no-parent", "child").getComment().orElse(null));
    }
}

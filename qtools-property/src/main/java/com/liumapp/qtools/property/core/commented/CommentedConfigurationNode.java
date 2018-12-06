package com.liumapp.qtools.property.core.commented;

import com.liumapp.qtools.property.core.ConfigurationNode;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * file CommentedConfigurationNode.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * A configuration node that can have a comment attached to it.
 */
public interface CommentedConfigurationNode extends ConfigurationNode {

    /**
     * Gets the current value for the comment.
     *
     * <p>If the comment contains multiple lines, the lines will be split by \n</p>
     *
     * @return The configuration's current comment
     */
    @NonNull
    Optional<String> getComment();

    /**
     * Sets the comment for this configuration node.
     *
     * @param comment The comment to set. Line breaks should be represented as LFs (\n)
     * @return this
     */
    @NonNull
    CommentedConfigurationNode setComment(@Nullable String comment);

    // Methods from superclass overridden to have correct return types
    @Nullable
    @Override CommentedConfigurationNode getParent();
    @NonNull
    @Override List<? extends CommentedConfigurationNode> getChildrenList();
    @NonNull
    @Override Map<Object, ? extends CommentedConfigurationNode> getChildrenMap();
    @NonNull
    @Override CommentedConfigurationNode setValue(@Nullable Object value);
    @NonNull
    @Override CommentedConfigurationNode mergeValuesFrom(@NonNull ConfigurationNode other);
    @NonNull
    @Override CommentedConfigurationNode getAppendedNode();
    @NonNull
    @Override CommentedConfigurationNode getNode(@NonNull Object... path);
    @NonNull
    @Override CommentedConfigurationNode copy();
}

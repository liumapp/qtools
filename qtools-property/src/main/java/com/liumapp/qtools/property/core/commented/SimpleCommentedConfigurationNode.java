package com.liumapp.qtools.property.core.commented;

import com.liumapp.qtools.property.core.ConfigurationNode;
import com.liumapp.qtools.property.core.ConfigurationOptions;
import com.liumapp.qtools.property.core.SimpleConfigurationNode;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * file SimpleCommentedConfigurationNode.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Basic implementation of {@link CommentedConfigurationNode}.
 */
public class SimpleCommentedConfigurationNode extends SimpleConfigurationNode implements CommentedConfigurationNode {
    private String comment =null;

    @NonNull
    public static SimpleCommentedConfigurationNode root() {
        return root(ConfigurationOptions.defaults());
    }

    @NonNull
    public static SimpleCommentedConfigurationNode root(@NonNull ConfigurationOptions options) {
        return new SimpleCommentedConfigurationNode(null, null, options);
    }

    protected SimpleCommentedConfigurationNode(@Nullable Object path, @Nullable SimpleConfigurationNode parent, @NonNull ConfigurationOptions options) {
        super(path, parent, options);
    }

    protected SimpleCommentedConfigurationNode(@Nullable SimpleConfigurationNode parent, @NonNull SimpleConfigurationNode copyOf) {
        super(parent, copyOf);
    }

    @NonNull
    @Override
    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

    @NonNull
    @Override
    public SimpleCommentedConfigurationNode setComment(@Nullable String comment) {
        attachIfNecessary();
        this.comment = comment;
        return this;
    }

    // Methods from superclass overridden to have correct return types

    @Nullable
    @Override
    public SimpleCommentedConfigurationNode getParent() {
        return (SimpleCommentedConfigurationNode) super.getParent();
    }

    @Override
    protected SimpleCommentedConfigurationNode createNode(Object path) {
        return new SimpleCommentedConfigurationNode(path, this, getOptions());
    }

    @NonNull
    @Override
    public SimpleCommentedConfigurationNode setValue(@Nullable Object value) {
        if (value instanceof CommentedConfigurationNode && ((CommentedConfigurationNode) value).getComment().isPresent()) {
            setComment(((CommentedConfigurationNode) value).getComment().get());
        }
        return (SimpleCommentedConfigurationNode) super.setValue(value);
    }

    @NonNull
    @Override
    public SimpleCommentedConfigurationNode mergeValuesFrom(@NonNull ConfigurationNode other) {
        if (other instanceof CommentedConfigurationNode) {
            Optional<String> otherComment = ((CommentedConfigurationNode) other).getComment();
            if (comment == null && otherComment.isPresent()) {
                comment = otherComment.get();
            }
        }
        return (SimpleCommentedConfigurationNode) super.mergeValuesFrom(other);
    }

    @NonNull
    @Override
    public SimpleCommentedConfigurationNode getNode(@NonNull Object... path) {
        return (SimpleCommentedConfigurationNode) super.getNode(path);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public List<? extends SimpleCommentedConfigurationNode> getChildrenList() {
        return (List<SimpleCommentedConfigurationNode>) super.getChildrenList();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public Map<Object, ? extends SimpleCommentedConfigurationNode> getChildrenMap() {
        return (Map<Object, SimpleCommentedConfigurationNode>) super.getChildrenMap();
    }

    @NonNull
    @Override
    public SimpleCommentedConfigurationNode getAppendedNode() {
        return (SimpleCommentedConfigurationNode) super.getAppendedNode();
    }

    @NonNull
    @Override
    public SimpleCommentedConfigurationNode copy() {
        return copy(null);
    }

    @NonNull
    @Override
    protected SimpleCommentedConfigurationNode copy(@Nullable SimpleConfigurationNode parent) {
        SimpleCommentedConfigurationNode copy = new SimpleCommentedConfigurationNode(parent, this);
        copy.comment = this.comment;
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleCommentedConfigurationNode)) return false;
        if (!super.equals(o)) return false;

        SimpleCommentedConfigurationNode that = (SimpleCommentedConfigurationNode) o;
        if (!Objects.equals(comment, that.comment)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(comment);
        return result;
    }

    @Override
    public String toString() {
        return "SimpleCommentedConfigurationNode{" +
                "super=" + super.toString() +
                ", comment=" + comment +
                '}';
    }
}

package com.liumapp.qtools.property.core.loader;

import com.liumapp.qtools.property.core.ConfigurationNode;
import com.liumapp.qtools.property.core.ConfigurationOptions;
import com.liumapp.qtools.property.core.SimpleConfigurationNode;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

/**
 * file TestConfigurationLoader.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 * This test configuration loader holds a single {@link ConfigurationNode}, {@code result}, that is updated when a node is saved and loaded when necessary.
 */
public class TestConfigurationLoader extends AbstractConfigurationLoader<ConfigurationNode> {
    private ConfigurationNode result;
    public static final class Builder extends AbstractConfigurationLoader.Builder<Builder> {

        @NonNull
        @Override
        public TestConfigurationLoader build() {
            return new TestConfigurationLoader(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    protected TestConfigurationLoader(Builder builder) {
        super(builder, CommentHandlers.values());
    }

    @Override
    protected void loadInternal(ConfigurationNode node, BufferedReader reader) throws IOException {
        node.setValue(result);
    }

    @Override
    protected void saveInternal(ConfigurationNode node, Writer writer) throws IOException {
        result.setValue(node);
    }

    public ConfigurationNode getNode() {
        return this.result;
    }

    public void setNode(ConfigurationNode node) {
        this.result = node;
    }

    /**
     * Return an empty node of the most appropriate type for this loader
     *
     * @param options The options to use with this node. Must not be null (take a look at {@link ConfigurationOptions#defaults()})
     * @return The appropriate node type
     */
    @NonNull
    @Override
    public ConfigurationNode createEmptyNode(@NonNull ConfigurationOptions options) {
        return SimpleConfigurationNode.root(options);
    }
}

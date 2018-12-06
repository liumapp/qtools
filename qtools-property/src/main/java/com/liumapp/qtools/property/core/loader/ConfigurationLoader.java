package com.liumapp.qtools.property.core.loader;

import com.liumapp.qtools.property.core.ConfigurationNode;
import com.liumapp.qtools.property.core.ConfigurationOptions;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;

/**
 * file ConfigurationLoader.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Represents an object which can load and save {@link ConfigurationNode} objects in a specific
 * configuration format.
 *
 * <p>An abstract implementation is provided by {@link AbstractConfigurationLoader}.</p>
 *
 * @param <NodeType> The {@link ConfigurationNode} type produced by the loader
 */
public interface ConfigurationLoader<NodeType extends ConfigurationNode> {

    /**
     * Gets the default {@link ConfigurationOptions} used by the loader.
     *
     * <p>New nodes will be created using the default options if a specific set is not defined.</p>
     *
     * @return The default options
     */
    @NonNull
    ConfigurationOptions getDefaultOptions();

    /**
     * Attempts to load a {@link ConfigurationNode} using this loader, from the defined source.
     *
     * <p>The resultant node represents the root of the configuration being loaded.</p>
     *
     * <p>The {@link #getDefaultOptions() default options} will be used to construct the resultant
     * configuration nodes.</p>
     *
     * @return The newly constructed node
     * @throws IOException if any sort of error occurs with reading or parsing the configuration
     */
    @NonNull
    default NodeType load() throws IOException {
        return load(getDefaultOptions());
    }

    /**
     * Attempts to load a {@link ConfigurationNode} using this loader, from the defined source.
     *
     * <p>The resultant node represents the root of the configuration being loaded.</p>
     *
     * @param options The options to load with
     * @return The newly constructed node
     * @throws IOException if any sort of error occurs with reading or parsing the configuration
     */
    @NonNull
    NodeType load(@NonNull ConfigurationOptions options) throws IOException;

    /**
     * Attempts to save a {@link ConfigurationNode} using this loader, to the defined sink.
     *
     * @throws IOException if any sort of error occurs with writing or generating the configuration
     */
    void save(@NonNull ConfigurationNode node) throws IOException;

    /**
     * Return an empty node of the most appropriate type for this loader, using the default options.
     *
     * @return The appropriate node type
     */
    @NonNull
    default NodeType createEmptyNode() {
        return createEmptyNode(getDefaultOptions());
    }

    /**
     * Return an empty node of the most appropriate type for this loader
     *
     * @param options The options to use with this node. Must not be null (see {@link ConfigurationOptions#defaults()})
     * @return The appropriate node type
     */
    @NonNull
    NodeType createEmptyNode(@NonNull ConfigurationOptions options);

    /**
     * Gets if this loader is capable of loading configurations.
     *
     * @return If this loader can load
     */
    default boolean canLoad() {
        return true;
    }

    /**
     * Gets if this loader is capable of saving configurations.
     *
     * @return If this loader can save
     */
    default boolean canSave() {
        return true;
    }
}

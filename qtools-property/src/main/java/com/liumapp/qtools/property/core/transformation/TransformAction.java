package com.liumapp.qtools.property.core.transformation;

import com.liumapp.qtools.property.core.ConfigurationNode;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * file TransformAction.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Represents an action to be performed that transforms a node in the configuration tree
 */
@FunctionalInterface
public interface TransformAction {

    /**
     * Called at a certain path, with the node at that path.
     *
     * <p>The state of the <code>inputPath</code> is only guaranteed to be accurate during a run of
     * the transform function. Use {@link NodePath#getArray()} if it's state needs to be stored.</p>
     *
     * @param inputPath The path of the given node
     * @param valueAtPath The node at the input path. May be modified
     * @return A modified path, or null if the path is to stay the same
     */
    @Nullable
    Object[] visitPath(ConfigurationTransformation.@NonNull NodePath inputPath, @NonNull ConfigurationNode valueAtPath);

}

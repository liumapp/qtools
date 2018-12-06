package com.liumapp.qtools.property.core.transformation;

import com.liumapp.qtools.property.core.ConfigurationNode;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.SortedMap;

/**
 * file VersionedTransformation.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Implements a number of child {@link ConfigurationTransformation}s which are only applied if required,
 * according to the configurations current version.
 */
class VersionedTransformation extends ConfigurationTransformation {
    private final Object[] versionPath;
    private final SortedMap<Integer, ConfigurationTransformation> versionTransformations;

    VersionedTransformation(Object[] versionPath, SortedMap<Integer, ConfigurationTransformation> versionTransformations) {
        this.versionPath = versionPath;
        this.versionTransformations = versionTransformations;
    }

    @Override
    public void apply(@NonNull ConfigurationNode node) {
        ConfigurationNode versionNode = node.getNode(versionPath);
        int currentVersion = versionNode.getInt(-1);
        for (SortedMap.Entry<Integer, ConfigurationTransformation> entry : versionTransformations.entrySet()) {
            if (entry.getKey() <= currentVersion) {
                continue;
            }
            entry.getValue().apply(node);
            currentVersion = entry.getKey();
        }
        versionNode.setValue(currentVersion);
    }
}

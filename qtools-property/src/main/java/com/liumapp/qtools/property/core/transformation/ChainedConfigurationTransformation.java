package com.liumapp.qtools.property.core.transformation;

import com.liumapp.qtools.property.core.ConfigurationNode;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;

/**
 * file ChainedConfigurationTransformation.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Implements a chain of {@link ConfigurationTransformation}s.s
 */
class ChainedConfigurationTransformation extends ConfigurationTransformation {
    private final ConfigurationTransformation[] transformations;

    ChainedConfigurationTransformation(ConfigurationTransformation[] transformations) {
        this.transformations = Arrays.copyOf(transformations, transformations.length);
    }

    @Override
    public void apply(@NonNull ConfigurationNode node) {
        for (ConfigurationTransformation transformation : transformations) {
            transformation.apply(node);
        }
    }
}

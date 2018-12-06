package com.liumapp.qtools.property.core.transformation;


import com.liumapp.qtools.property.core.ConfigurationNode;

/**
 * file MoveStrategy.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Strategy to use when moving a node from one path to another
 */
public enum MoveStrategy {

    /**
     * Moves nodes using {@link ConfigurationNode#mergeValuesFrom(ConfigurationNode)}.
     */
    MERGE {
        @Override
        public void move(ConfigurationNode source, ConfigurationNode target) {
            target.mergeValuesFrom(source);
        }
    },

    /**
     * Moves nodes using {@link ConfigurationNode#setValue(Object)}.
     */
    OVERWRITE {
        @Override
        public void move(ConfigurationNode source, ConfigurationNode target) {
            target.setValue(source);
        }
    };

    /**
     * Moves <code>source</code> to <code>target</code>.
     *
     * @param source The source node
     * @param target The target node
     */
    public abstract void move(ConfigurationNode source, ConfigurationNode target);
}

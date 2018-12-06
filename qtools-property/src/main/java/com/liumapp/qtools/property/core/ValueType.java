package com.liumapp.qtools.property.core;

/**
 * file ValueType.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * An enumeration of the types of value a {@link ConfigurationNode} can hold.
 */
public enum ValueType {

    /**
     * Represents a node that consists of a "single" scalar value
     */
    SCALAR,

    /**
     * Represents a node that consists of a number of child values, each mapped
     * by a unique key.
     */
    MAP,

    /**
     * Represents a node that consists of a number of child values, in a specific
     * order, each mapped by an index value.
     */
    LIST,

    /**
     * Represents a node that has no defined value.
     */
    NULL;

    /**
     * Gets if the type can hold child values.
     *
     * @return If the type can have children
     */
    public boolean canHaveChildren() {
        return this == MAP || this == LIST;
    }

}

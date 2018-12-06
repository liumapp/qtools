package com.liumapp.qtools.property.core.transformation;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Iterator;

/**
 * file NodePath.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Represents the path to a given node.
 */
public interface NodePath extends Iterable<Object> {

    /**
     * Gets a specific element from the path array
     *
     * @param i The index to get
     * @return Object at the index
     */
    Object get(int i);

    /**
     * Gets the length of the path
     *
     * @return Length of the path array
     */
    int size();

    /**
     * Returns a copy of the original path array
     *
     * @return the copied array
     */
    Object[] getArray();

    /**
     * Returns an iterator over the path.
     *
     * @return An iterator of the path
     */
    @NonNull
    @Override
    Iterator<Object> iterator();
}

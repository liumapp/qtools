package com.liumapp.qtools.property.core.objectmapping.serialize;

import com.google.common.reflect.TypeToken;
import com.liumapp.qtools.property.core.ConfigurationNode;
import com.liumapp.qtools.property.core.objectmapping.ObjectMappingException;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * file TypeSerializer.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Represents an object which can serialize and deserialize objects of a given type.
 *
 * @param <T> The type
 */
public interface TypeSerializer<T> {

    /**
     * Deserialize an object (of the correct type) from the given configuration node.
     *
     * @param type The type of return value required
     * @param value The node containing serialized data
     * @return An object
     * @throws ObjectMappingException If the presented data is invalid
     */
    @Nullable
    T deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode value) throws ObjectMappingException;

    /**
     * Serialize an object to the given configuration node.
     *
     * @param type The type of the input object
     * @param obj The object to be serialized
     * @param value The node to write to
     * @throws ObjectMappingException If the object cannot be serialized
     */
    void serialize(@NonNull TypeToken<?> type, @Nullable T obj, @NonNull ConfigurationNode value) throws ObjectMappingException;

}

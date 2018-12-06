package com.liumapp.qtools.property.core.objectmapping;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * file ObjectMapperFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * A factory to produce {@link ObjectMapper} instancess
 */
public interface ObjectMapperFactory {

    /**
     * Creates an {@link ObjectMapper} for the given type.
     *
     * @param type The type
     * @param <T> The type
     * @return An object mapper
     * @throws ObjectMappingException If an exception occured whilst mapping
     */
    @NonNull
    <T> ObjectMapper<T> getMapper(@NonNull Class<T> type) throws ObjectMappingException;

}

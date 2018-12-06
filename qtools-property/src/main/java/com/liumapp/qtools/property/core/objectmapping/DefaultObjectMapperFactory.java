package com.liumapp.qtools.property.core.objectmapping;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.ExecutionException;

/**
 * file DefaultObjectMapperFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Factory for a basic {@link ObjectMapper}.
 */
public class DefaultObjectMapperFactory implements ObjectMapperFactory {
    private static final ObjectMapperFactory INSTANCE = new DefaultObjectMapperFactory();

    @NonNull
    public static ObjectMapperFactory getInstance() {
        return INSTANCE;
    }

    private final LoadingCache<Class<?>, ObjectMapper<?>> mapperCache = CacheBuilder.newBuilder()
            .weakKeys()
            .maximumSize(500)
            .build(new CacheLoader<Class<?>, ObjectMapper<?>>() {
                @Override
                public ObjectMapper<?> load(Class<?> key) throws Exception {
                    return new ObjectMapper<>(key);
                }
            });

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> ObjectMapper<T> getMapper(@NonNull Class<T> type) throws ObjectMappingException {
        Preconditions.checkNotNull(type, "type");
        try {
            return (ObjectMapper<T>) mapperCache.get(type);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof ObjectMappingException) {
                throw (ObjectMappingException) e.getCause();
            } else {
                throw new ObjectMappingException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "DefaultObjectMapperFactory{}";
    }
}

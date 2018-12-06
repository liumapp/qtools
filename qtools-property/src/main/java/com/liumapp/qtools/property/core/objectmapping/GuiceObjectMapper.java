package com.liumapp.qtools.property.core.objectmapping;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * file GuiceObjectMapper.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * This subclass creates new object instances using a provided {@link Injector}.
 *
 * <p>This allows configuration objects to take additional arguments with Guice.</p>
 *
 * <p>Instances of this object should be reached using a {@link GuiceObjectMapperFactory}.</p>
 */
class GuiceObjectMapper<T> extends ObjectMapper<T> {
    private final Injector injector;
    private final Key<T> typeKey;

    /**
     * Create a new object mapper of a given type
     *
     * @param clazz The type this object mapper will work with
     * @throws ObjectMappingException if the provided class is in someway invalid
     */
    protected GuiceObjectMapper(@NonNull Injector injector, @NonNull Class<T> clazz) throws ObjectMappingException {
        super(clazz);
        this.injector = injector;
        this.typeKey = Key.get(clazz);
    }

    @Override
    public boolean canCreateInstances() {
        try {
            injector.getProvider(typeKey);
            return true;
        } catch (ConfigurationException ex) {
            return false;
        }
    }

    @Override
    protected T constructObject() throws ObjectMappingException {
        return injector.getInstance(this.typeKey);
    }
}

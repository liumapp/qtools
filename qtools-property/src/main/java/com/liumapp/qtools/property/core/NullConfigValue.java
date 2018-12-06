package com.liumapp.qtools.property.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;

/**
 * file NullConfigValue.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * A {@link ConfigValue} which holds no value.
 */
class NullConfigValue extends ConfigValue {
    NullConfigValue(SimpleConfigurationNode holder) {
        super(holder);
    }

    @Override
    ValueType getType() {
        return ValueType.NULL;
    }

    @Nullable
    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(@Nullable Object value) {
    }

    @Nullable
    @Override
    SimpleConfigurationNode putChild(@NonNull Object key, @Nullable SimpleConfigurationNode value) {
        return null;
    }

    @Nullable
    @Override
    SimpleConfigurationNode putChildIfAbsent(@NonNull Object key, @Nullable SimpleConfigurationNode value) {
        return null;
    }

    @Nullable
    @Override
    public SimpleConfigurationNode getChild(@Nullable Object key) {
        return null;
    }

    @NonNull
    @Override
    public Iterable<SimpleConfigurationNode> iterateChildren() {
        return Collections.emptySet();
    }

    @NonNull
    @Override
    NullConfigValue copy(@NonNull SimpleConfigurationNode holder) {
        return new NullConfigValue(holder);
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean equals(Object o) {
        return o instanceof NullConfigValue;
    }

    @Override
    public int hashCode() {
        return 1009;
    }

    @Override
    public String toString() {
        return "NullConfigValue{}";
    }
}

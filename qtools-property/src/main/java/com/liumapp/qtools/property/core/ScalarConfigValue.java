package com.liumapp.qtools.property.core;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;

/**
 * file ScalarConfigValue.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * A {@link ConfigValue} which holds a single ("scalar") value.s
 */
class ScalarConfigValue extends ConfigValue {
    private volatile Object value;

    ScalarConfigValue(SimpleConfigurationNode holder) {
        super(holder);
    }

    @Override
    ValueType getType() {
        return ValueType.SCALAR;
    }

    @Nullable
    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(@Nullable Object value) {
        Preconditions.checkNotNull(value);
        if (!holder.getOptions().acceptsType(value.getClass())) {
            throw new IllegalArgumentException("Configuration does not accept objects of type " + value.getClass());
        }
        this.value = value;
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
    ScalarConfigValue copy(@NonNull SimpleConfigurationNode holder) {
        ScalarConfigValue copy = new ScalarConfigValue(holder);
        copy.value = this.value;
        return copy;
    }

    @Override
    public void clear() {
       this.value = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScalarConfigValue that = (ScalarConfigValue) o;
        return Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "ScalarConfigValue{value=" + this.value + '}';
    }
}

package com.liumapp.qtools.property.core;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * file ListConfigValue.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * A {@link ConfigValue} which holds a list of values.
 */
class ListConfigValue extends ConfigValue {
    final AtomicReference<List<SimpleConfigurationNode>> values = new AtomicReference<>(new ArrayList<>());

    ListConfigValue(SimpleConfigurationNode holder) {
        super(holder);
    }

    @Override
    ValueType getType() {
        return ValueType.LIST;
    }

    ListConfigValue(SimpleConfigurationNode holder, Object startValue) {
        super(holder);

        SimpleConfigurationNode child = holder.createNode(0);
        child.attached = true;
        child.setValue(startValue);
        this.values.get().add(child);
    }

    @Nullable
    @Override
    public Object getValue() {
        final List<SimpleConfigurationNode> values = this.values.get();
        synchronized (values) {
            final List<Object> ret = new ArrayList<>(values.size());
            for (SimpleConfigurationNode obj : values) {
                ret.add(obj.getValue()); // unwrap
            }
            return ret;
        }
    }

    @Override
    public void setValue(@Nullable Object value) {
        if (!(value instanceof Collection)) {
            value = Collections.singleton(value);
        }
        final Collection<?> valueAsList = (Collection<?>) value;
        final List<SimpleConfigurationNode> newValue = new ArrayList<>(valueAsList.size());

        int count = 0;
        for (Object o : valueAsList) {
            if (o == null) {
                continue;
            }

            SimpleConfigurationNode child = holder.createNode(count);
            newValue.add(count, child);
            child.attached = true;
            child.setValue(o);
            ++count;
        }
        detachNodes(values.getAndSet(newValue));
    }

    @Nullable
    @Override
    public SimpleConfigurationNode putChild(@NonNull Object key, @Nullable SimpleConfigurationNode value) {
        return putChild((int) key, value, false);
    }

    @Nullable
    @Override
    SimpleConfigurationNode putChildIfAbsent(@NonNull Object key, @Nullable SimpleConfigurationNode value) {
        return putChild((int) key, value, true);
    }

    private SimpleConfigurationNode putChild(int index, @Nullable SimpleConfigurationNode value, boolean onlyIfAbsent) {
        SimpleConfigurationNode ret = null;
        List<SimpleConfigurationNode> values;
        do {
            values = this.values.get();
            synchronized (values) {
                if (value == null) {
                    if (index < values.size()) {
                        // remove the value
                        ret = values.remove(index);
                        // update indexes for subsequent elements
                        for (int i = index; i < values.size(); ++i) {
                            values.get(i).key = index;
                        }
                    }
                } else {
                    // check if the index is in range
                    if (index >= 0 && index < values.size()) {
                        if (onlyIfAbsent) {
                            return values.get(index);
                        } else {
                            ret = values.set(index, value);
                        }
                    } else if (index == -1) { // Gotta correct the child path for the correct path name
                        values.add(value);
                        value.key = values.lastIndexOf(value);
                    } else {
                        values.add(index, value);
                    }
                }
            }
        } while (!this.values.compareAndSet(values, values));
        return ret;
    }


    @Nullable
    @Override
    public SimpleConfigurationNode getChild(@Nullable Object key) {
        Integer value = Types.asInt(key);
        if (value == null || value < 0) {
            return null;
        }

        final List<SimpleConfigurationNode> values = this.values.get();
        synchronized (values) {
            if (value >= values.size()) {
                return null;
            }
            return values.get(value);
        }
    }

    @NonNull
    @Override
    public Iterable<SimpleConfigurationNode> iterateChildren() {
        List<SimpleConfigurationNode> values = this.values.get();
        synchronized (values) {
            return ImmutableList.copyOf(values);
        }
    }

    @NonNull
    @Override
    ListConfigValue copy(@NonNull SimpleConfigurationNode holder) {
        ListConfigValue copy = new ListConfigValue(holder);
        List<SimpleConfigurationNode> copyValues;

        final List<SimpleConfigurationNode> values = this.values.get();
        synchronized (values) {
            copyValues = new ArrayList<>(values.size());
            for (SimpleConfigurationNode obj : values) {
                copyValues.add(obj.copy(holder)); // recursively copy
            }
        }

        copy.values.set(copyValues);
        return copy;
    }

    private static void detachNodes(List<SimpleConfigurationNode> children) {
        synchronized (children) {
            for (SimpleConfigurationNode node : children) {
                node.attached = false;
                node.clear();
            }
        }
    }

    @Override
    public void clear() {
        List<SimpleConfigurationNode> oldValues = values.getAndSet(new ArrayList<>());
        detachNodes(oldValues);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListConfigValue that = (ListConfigValue) o;
        return Objects.equal(values.get(), that.values.get());
    }

    @Override
    public int hashCode() {
        return values.get().hashCode();
    }

    @Override
    public String toString() {
        return "ListConfigValue{values=" + this.values.get().toString() + '}';
    }
}

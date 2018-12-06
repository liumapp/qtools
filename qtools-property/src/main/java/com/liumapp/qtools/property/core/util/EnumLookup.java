package com.liumapp.qtools.property.core.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * file EnumLookup.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Utility class to cache more flexible enum lookup.
 *
 * <p>While normally case and punctuation have to match exactly, this method performs lookup that:</p>
 * <p>
 * <ul>
 *     <li>is case-insensitive</li>
 *     <li>ignores underscores</li>
 *     <li>caches mappings</li>
 * </ul>
 *
 * <p>If the enum has two fields that are equal except for case and underscores, an exact match
 * will return the appropriate value, and any fuzzy matches will map to the first value in the enum
 * that is applicable.</p>
 */
public final class EnumLookup {
    private EnumLookup() {}

    private static final LoadingCache<Class<? extends Enum<?>>, Map<String, Enum<?>>> ENUM_FIELD_CACHE = CacheBuilder
            .newBuilder()
            .weakKeys()
            .maximumSize(512)
            .build(new CacheLoader<Class<? extends Enum<?>>, Map<String, Enum<?>>>() {
                @Override
                public Map<String, Enum<?>> load(Class<? extends Enum<?>> key) {
                    Map<String, Enum<?>> ret = new HashMap<>();
                    for (Enum<?> field : key.getEnumConstants()) {
                        ret.put(field.name(), field);
                        ret.putIfAbsent(processKey(field.name()), field);
                    }
                    return ImmutableMap.copyOf(ret);
                }
            });

    @NonNull
    private static String processKey(@NonNull String key) {
        // stick a flower at the front so processed keys are different from literal keys
        return "🌸" + key.toLowerCase().replace("_", "");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public static <T extends Enum<T>> Optional<T> lookupEnum(@NonNull Class<T> clazz, @NonNull String key) {
        checkNotNull(clazz, "clazz");
        checkNotNull(key, "key");
        try {
            Map<String, Enum<?>> vals = ENUM_FIELD_CACHE.get(clazz);
            Enum<?> possibleRet = vals.get(key);
            if (possibleRet != null) {
                return Optional.of((T) possibleRet);
            }
            return Optional.ofNullable((T) vals.get(processKey(key)));
        } catch (ExecutionException e) {
            return Optional.empty();
        }
    }
}

package com.liumapp.qtools.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;
import static com.liumapp.qtools.core.classloader.ClassUtils.*;
import static com.liumapp.qtools.core.functions.Streams.*;
import static com.liumapp.qtools.core.utils.Predicates.*;


/**
 * @file TypeUtils.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/2 14:45
 */
public interface TypeUtils {


    Predicate<Class<?>> NON_OBJECT_TYPE_FILTER = t -> !Objects.equals(Object.class, t);

    static boolean isParameterizedType(Type type) {
        return type instanceof ParameterizedType;
    }

    static Type getRawType(Type type) {
        if (isParameterizedType(type)) {
            return ((ParameterizedType) type).getRawType();
        } else {
            return type;
        }
    }

    static Class<?> getRawClass(Type type) {
        Type rawType = getRawType(type);
        if (isClass(rawType)) {
            return (Class) rawType;
        }
        return null;
    }

    static boolean isClass(Type type) {
        return type instanceof Class;
    }

    static <T> Class<T> findActualTypeArgument(Type type, Class<?> interfaceClass, int index) {
        return (Class<T>) findActualTypeArguments(type, interfaceClass).get(index);
    }

    static List<Class<?>> findActualTypeArguments(Type type, Class<?> interfaceClass) {

        List<Class<?>> actualTypeArguments = new LinkedList<>();

        getAllGenericTypes(type, t -> isAssignableFrom(interfaceClass, getRawClass(t)))
                .forEach(parameterizedType -> {
                    Class<?> rawClass = getRawClass(parameterizedType);
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    for (int i = 0; i < typeArguments.length; i++) {
                        Type typeArgument = typeArguments[i];
                        if (typeArgument instanceof Class) {
                            actualTypeArguments.add(i, (Class) typeArgument);
                        }
                    }
                    Class<?> superClass = rawClass.getSuperclass();
                    if (superClass != null) {
                        actualTypeArguments.addAll(findActualTypeArguments(superClass, interfaceClass));
                    }
                });

        return unmodifiableList(actualTypeArguments);
    }

    /**
     * Get the specified types' generic types(including super classes and interfaces) that are assignable from {@link ParameterizedType} interface
     *
     * @param type        the specified type
     * @param typeFilters one or more {@link Predicate}s to filter the {@link ParameterizedType} instance
     * @return non-null read-only {@link List}
     */
    static List<ParameterizedType> getGenericTypes(Type type, Predicate<ParameterizedType>... typeFilters) {

        Class<?> rawClass = getRawClass(type);

        if (rawClass == null) {
            return emptyList();
        }

        List<Type> genericTypes = new LinkedList<>();

        genericTypes.add(rawClass.getGenericSuperclass());
        genericTypes.addAll(asList(rawClass.getGenericInterfaces()));

        return unmodifiableList(
                filterList(genericTypes, TypeUtils::isParameterizedType)
                        .stream()
                        .map(ParameterizedType.class::cast)
                        .filter(and(typeFilters))
                        .collect(toList())
        );
    }

    /**
     * Get all generic types(including super classes and interfaces) that are assignable from {@link ParameterizedType} interface
     *
     * @param type        the specified type
     * @param typeFilters one or more {@link Predicate}s to filter the {@link ParameterizedType} instance
     * @return non-null read-only {@link List}
     */
    static List<ParameterizedType> getAllGenericTypes(Type type, Predicate<ParameterizedType>... typeFilters) {
        List<ParameterizedType> allGenericTypes = new LinkedList<>();
        // Add generic super classes
        allGenericTypes.addAll(getAllGenericSuperClasses(type, typeFilters));
        // Add generic super interfaces
        allGenericTypes.addAll(getAllGenericInterfaces(type, typeFilters));
        // wrap unmodifiable object
        return unmodifiableList(allGenericTypes);
    }

    /**
     * Get all generic super classes that are assignable from {@link ParameterizedType} interface
     *
     * @param type        the specified type
     * @param typeFilters one or more {@link Predicate}s to filter the {@link ParameterizedType} instance
     * @return non-null read-only {@link List}
     */
    static List<ParameterizedType> getAllGenericSuperClasses(Type type, Predicate<ParameterizedType>... typeFilters) {

        Class<?> rawClass = getRawClass(type);

        if (rawClass == null || rawClass.isInterface()) {
            return emptyList();
        }

        List<Class<?>> allTypes = new LinkedList<>();
        // Add current class
        allTypes.add(rawClass);
        // Add all super classes
        allTypes.addAll(getAllSuperClasses(rawClass, NON_OBJECT_TYPE_FILTER));

        List<ParameterizedType> allGenericSuperClasses = allTypes
                .stream()
                .map(Class::getGenericSuperclass)
                .filter(TypeUtils::isParameterizedType)
                .map(ParameterizedType.class::cast)
                .collect(Collectors.toList());

        return unmodifiableList(filterAll(allGenericSuperClasses, typeFilters));
    }

    /**
     * Get all generic interfaces that are assignable from {@link ParameterizedType} interface
     *
     * @param type        the specified type
     * @param typeFilters one or more {@link Predicate}s to filter the {@link ParameterizedType} instance
     * @return non-null read-only {@link List}
     */
    static List<ParameterizedType> getAllGenericInterfaces(Type type, Predicate<ParameterizedType>... typeFilters) {

        Class<?> rawClass = getRawClass(type);

        if (rawClass == null) {
            return emptyList();
        }

        List<Class<?>> allTypes = new LinkedList<>();
        // Add current class
        allTypes.add(rawClass);
        // Add all super classes
        allTypes.addAll(getAllSuperClasses(rawClass, NON_OBJECT_TYPE_FILTER));
        // Add all super interfaces
        allTypes.addAll(getAllInterfaces(rawClass));

        List<ParameterizedType> allGenericInterfaces = allTypes
                .stream()
                .map(Class::getGenericInterfaces)
                .map(Arrays::asList)
                .flatMap(Collection::stream)
                .filter(TypeUtils::isParameterizedType)
                .map(ParameterizedType.class::cast)
                .collect(toList());

        return unmodifiableList(filterAll(allGenericInterfaces, typeFilters));
    }

    static String getClassName(Type type) {
        return getRawType(type).getTypeName();
    }

    static Set<String> getClassNames(Iterable<? extends Type> types) {
        return stream(types.spliterator(), false)
                .map(TypeUtils::getClassName)
                .collect(toSet());
    }
    
}

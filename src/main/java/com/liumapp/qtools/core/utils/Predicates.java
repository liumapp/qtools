package com.liumapp.qtools.core.utils;

import java.util.function.Predicate;

import static java.util.stream.Stream.of;

/**
 * @file Predicates.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/2 14:47
 */
public interface Predicates {

    Predicate[] EMPTY_ARRAY = new Predicate[0];

    /**
     * {@link Predicate} always return <code>true</code>
     *
     * @param <T> the type to test
     * @return <code>true</code>
     */
    static <T> Predicate<T> alwaysTrue() {
        return e -> true;
    }

    /**
     * {@link Predicate} always return <code>false</code>
     *
     * @param <T> the type to test
     * @return <code>false</code>
     */
    static <T> Predicate<T> alwaysFalse() {
        return e -> false;
    }

    /**
     * a composed predicate that represents a short-circuiting logical AND of {@link Predicate predicates}
     *
     * @param predicates {@link Predicate predicates}
     * @param <T>        the type to test
     * @return non-null
     */
    static <T> Predicate<T> and(Predicate<T>... predicates) {
        return of(predicates).reduce((a, b) -> a.and(b)).orElseGet(Predicates::alwaysTrue);
    }

    /**
     * a composed predicate that represents a short-circuiting logical OR of {@link Predicate predicates}
     *
     * @param predicates {@link Predicate predicates}
     * @param <T>        the detected type
     * @return non-null
     */
    static <T> Predicate<T> or(Predicate<T>... predicates) {
        return of(predicates).reduce((a, b) -> a.or(b)).orElse(e -> true);
    }

}

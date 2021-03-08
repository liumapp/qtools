package com.liumapp.qtools.core.utils;

import junit.framework.Assert;
import junit.framework.TestCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @file ArrayUtilsTest.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/8 17:55
 */
public class ArrayUtilsTest extends TestCase {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Foo {
        private Long id;
        private String name;
    }

    @Test
    public void testStream () {
        List<Foo> fooList = new ArrayList<>();
        fooList.add(new Foo(1L, "aa"));
        fooList.add(new Foo(2L, "bb"));
        fooList.add(new Foo(3L, "cc"));
        fooList.add(new Foo(2L, "dd"));
        Map<Long, Foo> fooMap = fooList.stream().collect(Collectors.toMap(Foo::getId, Function.identity(), (k1 , k2) -> k2));
        Assert.assertEquals(3, fooMap.size());
    }


}
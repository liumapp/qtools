package com.liumapp.qtools.core.utils;

import junit.framework.Assert;
import junit.framework.TestCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
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

    private List<Foo> sourceList;

    private List<Foo> lessTargetList;

    private List<Foo> moreTargetList;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Foo {
        private Long id;
        private String name;
    }

    @Before
    public void init() {
        sourceList = new ArrayList<>();
        sourceList.add(new Foo(1L, "aa"));
        sourceList.add(new Foo(2L, "bb"));
        sourceList.add(new Foo(3L, "cc"));
        sourceList.add(new Foo(2L, "dd"));

        lessTargetList = new ArrayList<>();
        lessTargetList.add(new Foo(3L, "gg"));

        moreTargetList = new ArrayList<>();
        moreTargetList.add(new Foo(1L, "aa"));
        moreTargetList.add(new Foo(2L, "bb"));
        moreTargetList.add(new Foo(3L, "cc"));
        moreTargetList.add(new Foo(2L, "dd"));
        moreTargetList.add(new Foo(4L, "ee"));
    }

    @Test
    public void testStream () {
        Map<Long, Foo> fooMap = sourceList.stream().collect(Collectors.toMap(Foo::getId, Function.identity(), (k1 , k2) -> k2));
        Assert.assertEquals(3, fooMap.size());
    }


}
package com.liumapp.qtools.core.loader;

import com.liumapp.qtools.async.AsyncTool;
import com.liumapp.qtools.core.loading.LoadingStrategy;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @file ToolsLoaderTest.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/2 13:44
 */
public class ToolsLoaderTest extends TestCase {

    @Test
    public void testLoading () {
        ToolsLoader<AsyncTool> toolsLoader = ToolsLoader.getToolsLoader(AsyncTool.class);
        AsyncTool asyncTool = toolsLoader.getTool("default");
        String result = asyncTool.sayHello();
        Assert.assertEquals("from future async tool", result);
    }

    @Test
    public void testJavaSpiLoading () {
        ServiceLoader<LoadingStrategy> strategies = ServiceLoader.load(LoadingStrategy.class);
        Iterator<LoadingStrategy> iterator = strategies.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
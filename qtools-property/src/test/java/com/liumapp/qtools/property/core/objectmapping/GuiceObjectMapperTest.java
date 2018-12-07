package com.liumapp.qtools.property.core.objectmapping;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

/**
 * file GuiceObjectMapperTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 */
public class GuiceObjectMapperTest {

    private static class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(String.class).toInstance("test value");
        }
    }

    private static class ConfigClass {
        @Inject
        private ConfigClass(String msg) {
            Assert.assertEquals("test value", msg);
        }
    }

    @Test
    public void testCreateGuiceObjectMapper() throws ObjectMappingException {
        Injector injector = Guice.createInjector(new TestModule());
        GuiceObjectMapperFactory factory = injector.getInstance(GuiceObjectMapperFactory.class);
        ObjectMapper<ConfigClass> mapper = factory.getMapper(ConfigClass.class);
        Assert.assertTrue(mapper.canCreateInstances());
        Assert.assertNotNull(mapper.bindToNew().getInstance());
    }
}

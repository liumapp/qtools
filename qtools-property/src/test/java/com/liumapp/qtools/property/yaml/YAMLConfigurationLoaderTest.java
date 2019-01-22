package com.liumapp.qtools.property.yaml;


import com.liumapp.qtools.property.core.ConfigurationNode;
import com.liumapp.qtools.property.core.loader.ConfigurationLoader;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;


/**
 * file YAMLConfigurationLoaderTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 * Basic sanity checks for the loader
 */
public class YAMLConfigurationLoaderTest {

    @Test
    public void testSimpleLoading() throws IOException {
        URL url = getClass().getResource("/yaml/example.yml");
        ConfigurationLoader loader = YAMLConfigurationLoader.builder()
                .setURL(url).build();
        ConfigurationNode node = loader.load();
        assertEquals("unicorn", node.getNode("test", "op-level").getValue());
        assertEquals("dragon", node.getNode("other", "op-level").getValue());
        assertEquals("dog park", node.getNode("other", "location").getValue());

        Function<Object, Map<String, List>> f = o -> (HashMap<String, List>)o;
        List<Map<String, List>> fooList = new ArrayList<>(node.getNode("foo").getList(f));
        assertEquals(5, fooList.get(0).get("bar").size());
    }

    @Test
    public void testOnlyList () throws IOException {
//        URL url = getClass().getResource("/yaml/example.yml");
//        ConfigurationLoader loader = YAMLConfigurationLoader.builder()
//                .setURL(url).build();
//        ConfigurationNode node = loader.load();
//        Function<Integer, String> f = o ;
//        List<Map<String, List>> list = new ArrayList<>(node.getNode("foo", "bar").getList(f));
//        assertEquals(5, list.size());
    }
}

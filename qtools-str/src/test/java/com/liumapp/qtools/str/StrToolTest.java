package com.liumapp.qtools.str;

import com.liumapp.qtools.str.basic.StrTool;
import junit.framework.TestCase;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * file StrToolTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/23
 */
public class StrToolTest extends TestCase {

    public void testConvertParams2NVPS () {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("sex", "boy");
        map.put("age", "24");
        ArrayList<NameValuePair> result =  StrTool.covertParams2NVPS(map);
        System.out.println(result.toString());
    }

}

package com.liumapp.qtools.ignores.string;

import junit.framework.TestCase;
import org.apache.http.NameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

    public void testSpecialStr () throws UnsupportedEncodingException {
        String s1 = URLEncoder.encode("${s}", "UTF-8");
        System.out.println(s1);

        String s2 = URLDecoder.decode(s1, "UTF-8");
        System.out.println(s2);
    }

}

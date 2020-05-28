package com.liumapp.qtools.core.string;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Map;

/**
 * author liumapp
 * file StrTool.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/8
 */
public class StrTool {

    private StrTool() {
        throw new UnsupportedOperationException("not allowed to initialize");
    }

    /**
     * is null or empty
     * @param s string
     * @return boolean true/false
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * input: HashMap(...)
     * output: [sex=boy, name=zhangsan, age=24]
     * @param params Map (String, object)
     * @return ArrayList(NameValuePair)
     */
    public static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        if (params == null || params.size() == 0) {
            return pairs;
        }
        for (Map.Entry<String, Object> param : params.entrySet()) {
            Object value = param.getValue();
            if (value instanceof String[]) {
                String[] values = (String[]) value;
                for (String v : values) {
                    pairs.add(new BasicNameValuePair(param.getKey(), v));
                }
            } else {
                if (value instanceof Integer) {
                    value = Integer.toString((Integer) value);
                } else if (value instanceof Long) {
                    value = Long.toString((Long) value);
                }
                pairs.add(new BasicNameValuePair(param.getKey(), (String) value));
            }
        }
        return pairs;
    }

}

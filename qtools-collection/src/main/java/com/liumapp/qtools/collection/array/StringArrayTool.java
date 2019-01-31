package com.liumapp.qtools.collection.array;

/**
 * file StringArrayTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/1/31
 */
public class StringArrayTool {

    /**
     * Convert String[] to comma separated string
     * @param stringArray string array ["a", "b" ,"c"]
     * @return a string with comma 'a','b','c'
     */
    public static String StringArrayToCommaLine (String[] stringArray) {
        if (stringArray.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String item : stringArray) {
                stringBuilder.append("'").append(item.replace("'", "\\'")).append("',");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
        } else {
            return "";
        }
    }

}

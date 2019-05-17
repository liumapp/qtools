package com.liumapp.qtools.str.suffix;

/**
 * file SuffixTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/10/11
 */
public class SuffixTool {

    /**
     * is filename ends with specified suffix
     */
    public static boolean checkStringSuffix (String filename, String suffix) {
        if (filename == null || suffix == null)
            return false;

        suffix = buildSuffix(suffix);
        return filename.endsWith(suffix);
    }

    public static String deleteSuffix (String name) {
        if (name == null) return "";
        String result = name;
        if (checkStringHasSuffix(name)) {
            String[] tmp = name.split(".");
            result = tmp[0];
        }
        return result;
    }

    /**
     * is a specified string own it's suffix
     */
    public static boolean checkStringHasSuffix (String filename) {
        if (filename == null)
            return false;
        String[] results = filename.split(".");
        if (results.length == 1)
            return false;
        return true;
    }

    /**
     * is suffix begin with "."
     */
    public static boolean isSuffix (String suffix) {
        if (suffix == null)
            return false;
        if (suffix.charAt(0) == 46 && suffix.length() > 1) {
            return true;
        }
        return false;
    }

    /**
     * make a string to suffix
     */
    public static String buildSuffix (String str) {
        if (str == null)
            return null;

        if (isSuffix(str))
            return str;

        return "." + str;
    }

}

package com.liumapp.qtools.array;

import java.io.File;
import java.util.Arrays;

/**
 * file ArrayTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/5/28
 */
public class ArrayTool {

    private ArrayTool () {
        throw new UnsupportedOperationException("can not be initialized");
    }

    /**
     * clear array
     * remove empty and null elements
     * support strings, files and objects .
     */
    public static Object[] clearArray (Object[] objects) {
        if (objects instanceof String[])
            return clearStringArray( (String[]) objects);
        else if (objects instanceof File[])
            return clearFileArray( (File[]) objects);
        else
            return clearObjectArray(objects);
    }

    /**
     * search the first value in two-dimensional int array
     * return postion array if founded
     * else return null
     */
    public static int[] searchIndexFromTwoDimensionalIntArray (int[][] array, int value) {
        int positionX = -1;
        int positionY =  -1;
        PARENT_LOOP: for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == value) {
                    positionX = i;
                    positionY = j;
                    break PARENT_LOOP;
                }
            }
        }
        if (positionX == -1 || positionY == -1) {
            return null;
        } else {
            int[] result = {positionX, positionY};
            return result;
        }
    }

    private static Object[] clearObjectArray (Object[] objectsArray) {
        return Arrays.stream(objectsArray)
                .filter(o -> (o != null))
                .toArray(Object[]::new);
    }

    private static String[] clearStringArray (String[] stringArray) {
        return Arrays.stream(stringArray)
                .filter(s -> (s != null && s.length() > 0))
                .toArray(String[]::new);
    }

    private static File[] clearFileArray (File[] fileArray) {
        return Arrays.stream(fileArray)
                .filter(f -> (f != null && f.length() > 0))
                .toArray(File[]::new);
    }



    /**
     * Convert String[] to comma separated string
     * @param stringArray string array ["a", "b" ,"c"]
     * @return a string with comma 'a','b','c'
     */
    public static String convertStringsToString (String[] stringArray) {
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

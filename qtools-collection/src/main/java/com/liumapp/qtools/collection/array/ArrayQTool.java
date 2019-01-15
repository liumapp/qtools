package com.liumapp.qtools.collection.array;

import java.io.File;
import java.util.Arrays;

/**
 * file ArrayQTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/1/15
 */
public class ArrayQTool {

    private Object[] objects;

    public ArrayQTool() {
    }

    public ArrayQTool(Object[] objects) {
        this.objects = objects;
    }

    /**
     * clear array
     * remove empty and null elements
     * support strings, files and objects .
     */
    public Object[] clearArray () {
        if (this.objects instanceof String[])
            this.objects = clearStringArray( (String[]) this.objects);
        else if (this.objects instanceof File[])
            this.objects = clearFileArray( (File[]) this.objects);
        else
            this.objects = clearObjectArray(this.objects);
        return this.objects;
    }

    /**
     * search the first value in two-dimensional int array
     * return postion array if founded
     * else return null
     */
    public int[] searchIndexFromTwoDimensionalIntArray (int[][] array, int value) {
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


    private Object[] clearObjectArray (Object[] objectsArray) {
        return Arrays.stream(objectsArray)
                .filter(o -> (o != null))
                .toArray(Object[]::new);
    }

    private String[] clearStringArray (String[] stringArray) {
        return Arrays.stream(stringArray)
                .filter(s -> (s != null && s.length() > 0))
                .toArray(String[]::new);
    }

    private File[] clearFileArray (File[] fileArray) {
        return Arrays.stream(fileArray)
                .filter(f -> (f != null && f.length() > 0))
                .toArray(File[]::new);
    }

    public Object[] getObjects() {
        return objects;
    }

    public ArrayQTool setObjects(Object[] objects) {
        this.objects = objects;
        return this;
    }
}

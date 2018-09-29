package com.liumapp.qtools.collection.array;

import java.io.File;

/**
 * file ArrayTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/29
 */
public class ArrayTool {

    /**
     * clear array
     * remove empty and null elements
     * support strings, files and objects .
     */
    public Object[] clearArray (Object[] objects) {
        if (objects instanceof String[])
            return clearStringArray( (String[]) objects);
        else if (objects instanceof File[])
            return clearFileArray( (File[]) objects);
        else
            return clearObjectArray(objects);
    }

    private Object[] clearObjectArray (Object[] objectsArray) {

    }

    private String[] clearStringArray (String[] stringArray) {

    }

    private File[] clearFileArray (File[] fileArray) {

    }

}

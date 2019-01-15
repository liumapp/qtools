package com.liumapp.qtools.collection.array;

/**
 * file AdvancedArrayTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/1/15
 */
public class AdvancedArrayTool {

    private Object[] objects;

    public AdvancedArrayTool() {
    }

    public AdvancedArrayTool(Object[] objects) {
        this.objects = objects;
    }

    

    public Object[] getObjects() {
        return objects;
    }

    public AdvancedArrayTool setObjects(Object[] objects) {
        this.objects = objects;
        return this;
    }
}

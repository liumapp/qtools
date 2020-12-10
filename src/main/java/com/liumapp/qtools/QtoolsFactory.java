package com.liumapp.qtools;

/**
 * file QtoolsFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class QtoolsFactory {


    private Qtools qtools;

    private static QtoolsFactory INSTANCE;

    private QtoolsFactory() {
    }

    public static synchronized Qtools getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QtoolsFactory();
            buildingQtools(INSTANCE);
        }
        return INSTANCE.qtools;
    }

    private static void buildingQtools (QtoolsFactory instance) {
        instance.qtools = new Qtools();
    }


}

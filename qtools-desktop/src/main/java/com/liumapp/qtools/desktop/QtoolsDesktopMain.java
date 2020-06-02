package com.liumapp.qtools.desktop;

import com.liumapp.qtools.desktop.spring.SpringLocator;
import com.liumapp.qtools.desktop.ui.QtoolsPannel;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * file QtoolsDesktopMain.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/5/28
 */
public class QtoolsDesktopMain {

    public static void main (String[] args) {
        SpringLocator.applicationContext = new ClassPathXmlApplicationContext("classpath*:/spring/applicationContext*.xml");
        QtoolsPannel qtoolsPannel = SpringLocator.getBean(QtoolsPannel.class);
        qtoolsPannel.start();
    }

}

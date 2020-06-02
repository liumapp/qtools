package com.liumapp.qtools.desktop;

import com.liumapp.qtools.desktop.spring.SpringLocator;
import com.liumapp.qtools.desktop.ui.QtoolsPannel;
import mdlaf.*;
import mdlaf.animation.*;
import mdlaf.themes.JMarsDarkTheme;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.themes.MaterialOceanicTheme;
import mdlaf.utils.MaterialColors;
import org.jdesktop.swingx.JXTaskPane;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

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

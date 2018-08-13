package com.liumapp.qtools.pic.config;

import java.awt.*;

/**
 * 定制logo属性类
 * @author liumapp
 * @file MatrixToLogoImageConfig.java
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2018/8/13
 */
public class MatrixToLogoImageConfig {

    // logo默认边框颜色
    /**
     * logo默认边框颜色
     */
    public static final Color DEFAULT_BORDER_COLOR = Color.RED;

    // logo默认边框宽度
    /**
     * logo默认边框宽度
     */
    public static final int DEFAULT_BORDER = 2;

    // logo大小默认为照片的1/5
    /**
     * logo大小默认为照片的1/5
     */
    public static final int DEFAULT_LOGO_PART = 5;

    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;

    public MatrixToLogoImageConfig() {
        this(DEFAULT_BORDER_COLOR, DEFAULT_LOGO_PART);
    }

    public MatrixToLogoImageConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return border;
    }

    public int getLogoPart() {
        return logoPart;
    }

}

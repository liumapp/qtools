package com.liumapp.qtools.date;

/**
 * file DateType.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/1
 */
public enum DateType {
    /**
     * 年
     */
    YEAR(1),
    /**
     * 月
     */
    MONTH(2),
    /**
     * 日
     */
    DAY(3),
    /**
     * 时
     */
    HOUR(4),
    /**
     * 分
     */
    MINUTES(5),
    /**
     * 秒
     */
    SECONDS(6);

    private int mean;

    DateType(int mean) {
        this.mean = mean;
    }

    public int getMean() {
        return mean;
    }

    public DateType setMean(int mean) {
        this.mean = mean;
        return this;
    }
}
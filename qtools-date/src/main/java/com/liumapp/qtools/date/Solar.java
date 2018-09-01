package com.liumapp.qtools.date;

/**
 * the Gregorian calendar
 * file Solar.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/9/1
 */
public class Solar {
    private int solarYear;
    private int solarMonth;
    private int solarDay;

    public Solar(int solarYear, int solarMonth, int solarDay) {
        this.solarYear = solarYear;
        this.solarMonth = solarMonth;
        this.solarDay = solarDay;
    }

    public int getSolarYear() {
        return solarYear;
    }

    public Solar setSolarYear(int solarYear) {
        this.solarYear = solarYear;
        return this;
    }

    public int getSolarMonth() {
        return solarMonth;
    }

    public Solar setSolarMonth(int solarMonth) {
        this.solarMonth = solarMonth;
        return this;
    }

    public int getSolarDay() {
        return solarDay;
    }

    public Solar setSolarDay(int solarDay) {
        this.solarDay = solarDay;
        return this;
    }

    @Override
    public String toString() {
        return String.format("公历：%S年%S月%S日", solarYear, solarMonth, solarDay);
    }
}
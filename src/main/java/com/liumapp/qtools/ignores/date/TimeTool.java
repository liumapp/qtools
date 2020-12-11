package com.liumapp.qtools.ignores.date;

import com.liumapp.qtools.ignores.string.StrTool;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * file TimeTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/5/28
 */
public class TimeTool {


    public static final DateTimeFormatter DF_YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DF_HMS = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static final DateTimeFormatter DF_HM = DateTimeFormatter.ofPattern("HH:mm");

    public final static DateTimeFormatter DF_YMD_HMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public final static DateTimeFormatter DF_YMD_HMSSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final static int MINUTE_SECONDS = 60;

    public static String turn2String(LocalDate localDate) {
        localDate = localDate == null ? LocalDate.now() : localDate;
        return localDate.format(DF_YMD);
    }


    /**
     * 获取今天的日期，格式为yyyy-MM-dd，例如2018-06-21
     *
     * @return String
     */
    public static String getCurrentDate() {

        return DF_YMD.format(LocalDate.now());

    }

    public static String secondFormat(LocalDateTime time) {
        if (null == time) {
            return null;
        }

        return DF_YMD_HMS.format(time);
    }

    public static int minuteDuration(LocalDateTime from, LocalDateTime end) {
        Long duration = Duration.between(from, end).getSeconds();
        return (int) (duration / MINUTE_SECONDS);
    }


    /**
     * 四舍五入取整LocalTime
     *
     * @param localTime 时间
     * @return LocalTime
     */
    public static LocalTime roundLocalTime(LocalTime localTime) {

        if (null == localTime) {
            return null;
        }
        long seconds = localTime.getSecond();
        if (seconds >= 30) {
            return localTime.plusSeconds(60 - seconds);
        } else {
            return localTime.minusSeconds(seconds);
        }
    }

    /**
     * 获取当前时间，格式为"yyyy-MM-dd HH:mm:ss"，例如2018-06-28 11:36:30
     *
     * @return String
     */
    public static LocalDateTime transformLocalTimeToLocalDateTime(LocalTime localTime) {

        LocalDate localDate = LocalDate.now();
        return LocalDateTime.of(localDate, localTime);

    }

    /**
     * 将时间字符串转成LocalTime格式，格式为"HH:mm"，例如"05:30"
     *
     * @param str 时间字符串
     * @return LocalTime
     */
    public static LocalTime transformStrToLocalTime(String str) {

        if (StrTool.isSpace(str)) {
            return null;
        }

        try {
            return LocalTime.parse(str, DF_HM);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 将时间转成字符串格式，格式为"HH:mm"，例如"05:30"
     *
     * @param localDateTime 时间
     * @return String
     */
    public static String turnLocalDateTime2Hm(LocalDateTime localDateTime) {

        if (null == localDateTime) {
            return null;
        }

        try {
            return localDateTime.format(DF_HM);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 将时间字符串转成LocalDateTime格式，格式为"yyyy-MM-dd HH:mm:ss"，例如2018-06-28 11:36:30
     *
     * @param str 时间字符串
     * @return LocalDateTime
     */
    public static LocalDateTime transformStrToLocalDateTime(String str) {

        if (StrTool.isSpace(str)) {
            return null;
        }

        try {
            return LocalDateTime.parse(str, DF_YMD_HMS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 将时间字符串转成LocalDateTime格式，格式为"yyyy-MM-dd HH:mm:ss.SSS"，例如2018-06-28 11:36:30.123
     * @param str
     * @return
     */
    public static LocalDateTime transFormMsToLocalDateTime(String str) {

        if (StrTool.isSpace(str)) {
            return null;
        }

        try {
            return LocalDateTime.parse(str,DF_YMD_HMSSS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取当前时间，格式为"yyyy-MM-dd HH:mm:ss"，例如2018-06-28 11:36:30
     *
     * @return String
     */
    public static String getCurrentLocalDateTime() {

        return DF_YMD_HMS.format(LocalDateTime.now());

    }


    /**
     * 获取当前时间，格式为"HH:mm:ss"，例如11:36:30
     *
     * @return String
     */
    public static String getCurrentLocalTime() {

        return DF_HMS.format(LocalTime.now());

    }


    /**
     * 获取两个LocalDateTime之间的秒数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return long
     */
    public static long getDurationSeconds(LocalDateTime start, LocalDateTime end) {

        if (null == start || null == end) {
            return 0;
        }

        Duration duration = Duration.between(start, end);

        return duration.toMillis() / 1000;

    }

    /**
     * 获取两个LocalDateTime之间的分钟数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return long
     */
    public static long getDurationMinutes(LocalDateTime start, LocalDateTime end) {

        if (null == start || null == end) {
            return 0;
        }
        Duration duration = Duration.between(start, end);

        return duration.toMinutes();

    }

    /**
     * 获取两个LocalDateTime之间的分钟数，格式化秒数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return long
     */
    public static int getDurationMinutesFormatSeconds(LocalDateTime start, LocalDateTime end) {

        if (null == start || null == end) {
            return 0;
        }
        LocalDateTime startTime = start.withSecond(0);
        LocalDateTime endTime = end.withSecond(0);
        Duration duration = Duration.between(startTime, endTime);

        return (int) duration.toMinutes();
    }

    /**
     * 把字符串(String)转为为日期(localDate)      "2019-05-14"
     *
     * @param str
     * @return
     */
    public static LocalDate transformStrToLocalDate(String str) {

        if (StrTool.isSpace(str)) {
            return null;
        }
        try {
            LocalDate localDate = LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            return localDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static LocalDateTime minuteFormat2Now(String time) {
        if (StrTool.isSpace(time)) {
            return null;
        }

        String[] tmp = time.split(":");
        LocalDateTime localDateTime = LocalDateTime.now();
        return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), Integer.valueOf(tmp[0]), Integer.valueOf(tmp[1]), 0);
    }


    /**
     * 将Time类型转换成分钟数，跨天则加上24小时的分钟数
     *
     * @param time 时间
     * @param isAcrossDay 是否跨天
     * @return int
     */
    public static int transformTimeToMinute(Time time, Boolean isAcrossDay){
        if (null == time){
            return 0;
        }

        String timeStr = time.toString();
        String[] strings = timeStr.split(":");
        int timeHour = Integer.parseInt(strings[0]);
        int timeMinute = Integer.parseInt(strings[1]);

        //跨天则加上一天的分钟数
        if (null != isAcrossDay && isAcrossDay){
            return timeMinute + timeHour * 60 + 1440;
        }

        return timeMinute + timeHour * 60;

    }

    /**
     * 将分钟数转换成Time类型
     *
     * @param minuteValue 分钟数
     * @return Time
     */
    public static Time transformMinuteToTime(int minuteValue){

        int timeHour = minuteValue / 60;
        int timeMinute = minuteValue % 60;
        String timeStr = String.valueOf(timeHour) + ":" + String.valueOf(timeMinute) + ":00";

        return Time.valueOf(timeStr);

    }

    /**
     * 将Time类型转换成秒数
     *
     * @param time 时间
     * @return int
     */
    public static int transformTimeToSecond(Time time){

        if (null == time){
            return 0;
        }

        String timeStr = time.toString();
        String[] strings = timeStr.split(":");
        int timeHour = Integer.parseInt(strings[0]);
        int timeMinute = Integer.parseInt(strings[1]);
        int timeSecond = Integer.parseInt(strings[2]);

        return timeSecond + timeMinute * 60 + timeHour * 3600;

    }

    /**
     * 将秒数转换成Time类型
     *
     * @param secondValue 秒数
     * @return Time
     */
    public static Time transformSecondToTime(int secondValue){

        int timeSecond = secondValue % 60;
        secondValue /= 60;
        int timeMinute = secondValue % 60;
        int timeHour = secondValue / 60;
        String timeStr = String.valueOf(timeHour) + ":" + String.valueOf(timeMinute) + ":" + String.valueOf(timeSecond);

        return Time.valueOf(timeStr);

    }

    /**
     * 获取当前时间，格式为"yyyy-MM-dd HH:mm:ss"，例如2018-06-28 11:36:30
     *
     * @return String
     */
    public static String getCurrentTime(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return dtf.format(LocalDateTime.now());

    }

    /**
     * 获取初始时间加上分钟数后的时间
     *
     * @param time 初始时间
     * @param minutes 分钟数
     * @return Time
     */
    public static Time getTimeAddMinutes(Time time, Integer minutes){

        if (null == time){
            return null;
        }
        if (null == minutes){
            return time;
        }

        int result = transformTimeToMinute(time, null) + minutes;
        if (result >= 1440){
            result -= 1440;
        }

        return transformMinuteToTime(result);

    }

    /**
     * 获取初始时间减去分钟数后的时间
     *
     * @param time 初始时间
     * @param minutes 分钟数
     * @return Time
     */
    public static Time getTimeMinusMinutes(Time time, Integer minutes){

        if (null == time){
            return null;
        }
        if (null == minutes){
            return time;
        }

        Integer result = transformTimeToMinute(time, null) - minutes;
        if (result < 0){
            result += 1440;
        }

        return transformMinuteToTime(result);

    }

    /**
     * 将Time类型格式化为时分格式的字符串，例如将"08:30:00"格式化为"08:30"
     * @param time 时间
     * @return String
     */
    public static String formatTimeHourMinute(Time time){

        if (null == time){
            return null;
        }

        String[] strings = time.toString().split(":");

        return strings[0] + ":" + strings[1];

    }

    /**
     * 将Time类型转换成分钟数
     *
     * @param timeStr 时间
     * @return int
     */
    public static int transformTimeStrToMinute(String timeStr){
        if (null == timeStr || timeStr.isEmpty()){
            return 0;
        }

        String[] strings = timeStr.split(":");
        int timeHour = Integer.parseInt(strings[0]);
        int timeMinute = Integer.parseInt(strings[1]);

        return timeMinute + timeHour * 60;

    }

    /**
     * 将分钟数转换成Time类型
     *
     * @param minuteValue 分钟数
     * @return String
     */
    public static String transformMinuteToTimeStr(int minuteValue){

        int timeHour = minuteValue / 60;
        int timeMinute = minuteValue % 60;
        String timeHourStr = String.valueOf(timeHour);
        if (timeHour < 10){
            timeHourStr = "0" + timeHourStr;
        }
        String timeMinuteStr = String.valueOf(timeMinute);
        if (timeMinute < 10){
            timeMinuteStr = "0" + timeMinuteStr;
        }

        return  timeHourStr + ":" + timeMinuteStr + ":" + "00";

    }

    /**
     * 将秒数转换成Time类型
     *
     * @param secondValue 秒数
     * @return String
     */
    public static String transformSecondToTimeStr(int secondValue){
        int timeSecond = secondValue % 60;
        secondValue /= 60;
        int timeMinute = secondValue % 60;
        int timeHour = secondValue / 60;

        return String.valueOf(timeHour) + ":" + String.valueOf(timeMinute) + ":"
                + String.valueOf(timeSecond);
    }

    /**
     * 获取跨天时间
     *
     * @param time 时间
     * @param startTime 始发时间
     * @return int
     */
    public static int getAcrossDayMinute(Time time, Time startTime) {
        if (time != null && startTime != null) {
            return transformTimeToMinute(time, time.before(startTime));
        } else if (time != null) {
            return transformTimeToMinute(time, null);
        }
        return 0;
    }

    /**
     * 格式化时间
     *
     * @param time 时间
     * @param format 格式
     * @return String
     */
    public static String getFormatTime(Time time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }

    /**
     * 秒转分钟  30以上进1
     * @param second 秒数
     * @return 分钟数
     */
    public static Integer transformSecond(Integer second) {
        if (null == second) {
            return null;
        }
        return second % 60 > 30 ? (second / 60 + 1) : second / 60;
    }

    public static Date formateTimeStrToDate(String timeStr, String dateFormat) throws ParseException {
        java.util.Date parse = new SimpleDateFormat(dateFormat).parse(timeStr);
        return new Date(parse.getTime());
    }

    /**
     * 时间格式化
     *
     * @param localDateTime 时间
     * @param formatter 格式类型
     * @return String
     */
    public static String localDateTimeFormat(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        if (null == localDateTime) {
            return null;
        }
        return localDateTime.format(formatter);
    }

    /**
     * 格式化localDateTime的秒数， 13:12:11 -> 13:12:00
     * @param localDateTime
     * @return
     */
    public static LocalDateTime formatSecondTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.withSecond(0);
    }



}

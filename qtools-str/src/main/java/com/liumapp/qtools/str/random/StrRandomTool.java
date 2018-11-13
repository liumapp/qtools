package com.liumapp.qtools.str.random;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * author liumapp
 * file StrRandomTool.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/13
 */
public final class StrRandomTool {

    private StrRandomTool () {
        throw new UnsupportedOperationException("not allowed to initialize");
    }

    private static final char[] AUTH_CODE_ALL = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'a', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            '3', '4', '5', '7', '8'};

    private static final char[] AUTH_CODE_NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final int AUTH_CODE_ALL_LENGTH = AUTH_CODE_ALL.length;

    private static final int AUTH_CODE_NUMBER_LENGTH = AUTH_CODE_NUMBER.length;

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * generate a random char
     */
    public static char getRandomAllChar() {
        return AUTH_CODE_ALL[getNumberRandom(0, AUTH_CODE_ALL_LENGTH)];
    }

    /**
     * generate a random number
     */
    public static String getRandomAllNumber(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(AUTH_CODE_NUMBER[getNumberRandom(0, AUTH_CODE_NUMBER_LENGTH)]);
        }
        return sb.toString();
    }

    /**
     * generate a random string , mixed number and string
     */
    public static String getRandom(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(AUTH_CODE_ALL[getNumberRandom(0, AUTH_CODE_ALL_LENGTH)]);
        }
        return sb.toString();
    }

    /**
     * generate a random number between min and max
     *
     * @param min min number
     * @param max bigger than min
     * @return int random number
     */
    public static int getNumberRandom(int min, int max) {
        return min + RANDOM.nextInt(max - min);
    }

    /**
     * generate a random number between 0 and number
     *
     * @param number number value
     * @return int random number
     */
    public static int getNumberRandom(int number) {
        return RANDOM.nextInt(number);
    }

    /**
     * 获取UUID by jdk
     */
    public static String getUuid(boolean is32bit) {
        String uuid = getUuid().toString();
        if (is32bit) {
            return uuid.replace("-", "");
        }
        return uuid;
    }

    /**
     * 获取原生UUID对象
     */
    public static UUID getUuid() {
        return UUID.randomUUID();
    }


}

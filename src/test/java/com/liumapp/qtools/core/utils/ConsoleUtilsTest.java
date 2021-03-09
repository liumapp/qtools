package com.liumapp.qtools.core.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Scanner;

/**
 * @file ConsoleUtilsTest.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/9 09:28
 */
public class ConsoleUtilsTest extends TestCase {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int lineNumber = scanner.nextInt();
        String[] strs = new String[lineNumber];
        for (int i = 0; i < lineNumber; i++) {
            strs[i] = scanner.nextLine();
        }


    }



}
package com.liumapp.qtools.core.annotations;

import java.lang.annotation.*;

/**
 * @file SPI.java
 * @author liumapp
 * @email liumapp.com@gmail.com
 * @homepage http://www.liumapp.com
 * @date 2021/3/2 09:35
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {

    String value() default "";

}

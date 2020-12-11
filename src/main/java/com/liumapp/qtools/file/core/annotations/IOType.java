package com.liumapp.qtools.file.core.annotations;

import com.liumapp.qtools.file.core.enums.IOEnum;

import java.lang.annotation.*;

/**
 * file IOType.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IOType {

    IOEnum value();

}

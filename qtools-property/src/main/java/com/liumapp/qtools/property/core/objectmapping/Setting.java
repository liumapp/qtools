package com.liumapp.qtools.property.core.objectmapping;

import java.lang.annotation.*;

/**
 * file Setting.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Marks a field to be mapped by an {@link ObjectMapper}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Setting {

    /**
     * The path this setting is located at
     *
     * @return The path
     */
    String value() default "";

    /**
     * The default comment associated with this configuration node
     * This will be applied to any comment-capable configuration loader
     *
     * @return The comment
     */
    String comment() default "";

}

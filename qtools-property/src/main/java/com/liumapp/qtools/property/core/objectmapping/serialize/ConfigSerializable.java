package com.liumapp.qtools.property.core.objectmapping.serialize;

import java.lang.annotation.*;

/**
 * file ConfigSerializable.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * This annotation is used to indicate that the given type is capable of being serialized and
 * deserialized by the configuration object mapper.
 *
 * <p>Types with this annotation must have a zero-argument constructor to be instantiated by the
 * object mapper (though already instantiated objects can be passed to the object mapper to be
 * populated with settings)</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ConfigSerializable {
}

package com.liumapp.qtools.property.core.objectmapping;

import com.google.common.reflect.TypeToken;

/**
 * file InvalidTypeException.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Extension of {@link ObjectMappingException} for instances where the present {@link TypeToken type}
 * is invalid.
 */
public class InvalidTypeException extends ObjectMappingException {

    public InvalidTypeException(TypeToken<?> received) {
        super("Invalid type presented to serializer: " + received);
    }

}

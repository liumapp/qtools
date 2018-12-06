package com.liumapp.qtools.property.core.objectmapping;

/**
 * file ObjectMappingException.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Exception thrown on errors encountered while mapping objects
 */
public class ObjectMappingException extends Exception {
    public ObjectMappingException() {
        super();
    }

    public ObjectMappingException(String message) {
        super(message);
    }

    public ObjectMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectMappingException(Throwable cause) {
        super(cause);
    }

    protected ObjectMappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

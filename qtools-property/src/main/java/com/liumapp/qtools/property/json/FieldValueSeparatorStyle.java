package com.liumapp.qtools.property.json;

/**
 * file FieldValueSeparatorStyle.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 * Enumeration of field value separator styles.
 */
public enum FieldValueSeparatorStyle {

    /**
     * Style which uses spaces either side of the <code>:</code> character.
     */
    SPACE_BOTH_SIDES(" : "),

    /**
     * Style which uses a space after the <code>:</code> character.
     */
    SPACE_AFTER(": "),

    /**
     * Style which uses no spaces.
     */
    NO_SPACE(":");

    private final String decorationType;

    FieldValueSeparatorStyle(String decorationType) {
        this.decorationType = decorationType;
    }

    public String getValue() {
        return decorationType;
    }
}

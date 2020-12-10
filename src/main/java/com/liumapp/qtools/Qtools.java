package com.liumapp.qtools;

import com.liumapp.qtools.file.string.StringFileFactory;
import com.liumapp.qtools.file.string.core.StringFile;
import com.liumapp.qtools.file.string.core.SupportStringFile;

import java.io.Serializable;

/**
 * file Qtools.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class Qtools implements Serializable, SupportStringFile {


    private static final long serialVersionUID = 5563619718620494936L;

    protected Qtools() {
    }

    @Override
    public StringFile stringFile() {
        return StringFileFactory.getInstance();
    }


}

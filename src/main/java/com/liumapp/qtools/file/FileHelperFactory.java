package com.liumapp.qtools.file;

import com.liumapp.qtools.core.AbstractFactory;
import com.liumapp.qtools.file.core.FileHelper;
import com.liumapp.qtools.file.core.annotations.IOType;
import com.liumapp.qtools.file.core.enums.IOEnum;
import com.liumapp.qtools.file.core.exceptions.CreateFileHelperException;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Set;

/**
 * file FileHelperFactory.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class FileHelperFactory extends AbstractFactory<FileHelper> implements Serializable {

    private static final long serialVersionUID = 5046605863380012214L;

    private static FileHelperFactory INSTANCE;

    private FileHelperParam fileHelperParam;

    private Reflections reflections = new Reflections(new ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage("com.liumapp.qtools.file"))
            .setScanners(new SubTypesScanner(), new MethodAnnotationsScanner(), new TypeAnnotationsScanner())
    );

    private FileHelperFactory() {
        fileHelperParam = new FileHelperParam();
    }

    public static synchronized FileHelperFactory getFactoryInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileHelperFactory();
        }
        return INSTANCE;
    }

    @Override
    public FileHelper build() {
        this.t = INSTANCE.createInstance();
        return this.t;
    }

    /**
     * 获取最近创建的一个实例
     * 如果没有，则使用默认配置新建一个实例返回
     * @return
     */
    @Override
    public FileHelper getInstance() {
        return this.createInstanceIfNotExists();
    }

    /**
     * 根据ioType动态创建需要的实例
     * @return
     */
    @Override
    protected FileHelper createInstance() {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(IOType.class);
        FileHelper result = null;
        for (Class<?> annotatedClass : annotated)
        {
            try {
                IOType annotation = annotatedClass.getAnnotation(IOType.class);
                if (annotation.value().equals(fileHelperParam.ioType)) {
                    Constructor<?> cons = annotatedClass.getConstructor(FileHelperParam.class);
                    result = (FileHelper) cons.newInstance(new FileHelperParam());
                }
            } catch (Exception e) {
                throw new CreateFileHelperException("不支持的IOType类型: " + fileHelperParam.ioType.getIoTypeName());
            }
        }
        return result;
    }

    public FileHelperFactory setIoType (IOEnum ioType) {
        fileHelperParam.ioType = ioType;
        return this;
    }

    public FileHelperFactory setSupportTransferTo (Boolean needSupportTransferTo) {
        fileHelperParam.supportTransferTo = needSupportTransferTo;
        return this;
    }

    public FileHelperFactory setAutoCreateFolder (Boolean needAutoCreateFolder) {
        fileHelperParam.autoCreateFolder = needAutoCreateFolder;
        return this;
    }


}

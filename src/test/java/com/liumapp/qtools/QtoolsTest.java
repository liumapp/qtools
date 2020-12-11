package com.liumapp.qtools;

import com.liumapp.qtools.file.FileHelperParam;
import com.liumapp.qtools.file.core.FileHelper;
import com.liumapp.qtools.file.core.annotations.IOType;
import com.liumapp.qtools.file.core.enums.IOEnum;
import junit.framework.TestCase;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * file QtoolsTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2020/12/10
 */
public class QtoolsTest extends TestCase {

    /**
     * 1. 根据base64文本创建文件;
     * 2. 通过零拷贝存储文件在系统磁盘;
     * 3. 如果磁盘目录不存在，则创建;
     * 4. 如果磁盘目录地址已有同名文件，则覆盖;
     */
    public void testBase64() throws IOException {
        Qtools qtools = QtoolsFactory.getFactoryInstance().getInstance();
        byte[] b = qtools.newFileHelperBuilder()
                .setAutoCreateFolder(true)
                .setIoType(IOEnum.NIO)
                .setSupportTransferTo(true)
                .build()
                .readyBytesByFilePath(this.getClass().getResource("/content.txt").getPath());
        System.out.println(new String(b,0, b.length));
    }

    public void testReflection() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.liumapp.qtools.file"))
                .setScanners(new SubTypesScanner(), new MethodAnnotationsScanner(), new TypeAnnotationsScanner())
        );

        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(IOType.class);
        for (Class<?> annotatedClass : annotated)
        {
            System.out.println(annotatedClass.getName());
            IOType annotation = annotatedClass.getAnnotation(IOType.class);

            String value = annotation.value().getIoTypeName();
            System.out.println(value);

            Constructor<?> cons = annotatedClass.getConstructor(FileHelperParam.class);
            FileHelper fileHelper = (FileHelper) cons.newInstance(new FileHelperParam());
            System.out.println(fileHelper.toString());
        }
    }

}

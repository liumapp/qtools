package com.liumapp.qtools.file.base64;

import com.liumapp.qtools.file.basic.FileTool;
import com.liumapp.qtools.file.load.LoadFileTool;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Base64;

/**
 * author liumapp
 * file Base64FileTool.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/7
 */
public class Base64FileTool {

    /**
     * all static method , plz do not initialize the class
     */
    private Base64FileTool() {
        throw new UnsupportedOperationException("not allowed to initialize");
    }

    /**
     * read file from file path , and return base64 value
     * @param path file path
     * @return file base64
     */
    public static String filePathToBase64 (String path) throws IOException {
        String base64 = null;
        File file = new File(path);
        byte[] bytes = LoadFileTool.loadFile(file);
        base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }

    /**
     * require a file object , return base64 value
     * @param file file object
     * @return file base64
     */
    public static String FileToBase64 (File file) throws IOException {
        String base64 = null;
        byte[] bytes = LoadFileTool.loadFile(file);
        base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }

    /**
     * read input stream and convert to base64
     * @param is input stream
     * @return base64 content
     * @throws IOException io exception
     */
    public static String inputStreamToBase64 (InputStream is) throws IOException {
        String base64 = Base64.getEncoder().encodeToString(IOUtils.toByteArray(is));
        return base64;
    }

    /**
     * create file from base64
     * @param base64Content base64 content string
     * @param savepath new file save path
     */
    public static void saveBase64File (String base64Content, String savepath) throws IOException {
        FileTool.createFile(new File(savepath));
        FileOutputStream fos = new FileOutputStream(savepath);
        try {
            fos.write(Base64.getDecoder().decode(base64Content));
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

}

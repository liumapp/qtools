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

//    public static String convertHexByteToBase64 (byte[] data) {
//
//    }

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
     * @param fileSavePath new file save path
     */
    public static void saveBase64File (String base64Content, String fileSavePath) throws IOException {
        saveFile(removeBase64Header(base64Content), fileSavePath);
    }

    private static void saveFile (String base64ContentWithoutHeader, String fileSavePath) throws IOException {
        FileTool.createFile(new File(fileSavePath));
        FileOutputStream fos = new FileOutputStream(fileSavePath);
        try {
            fos.write(Base64.getDecoder().decode(base64ContentWithoutHeader));
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * return a base64 content without header
     */
    public static String removeBase64Header (String base64Content) {
        if (CheckBase64Tool.checkHeader(base64Content))
            return removeHeader(base64Content);
        return base64Content;
    }

    private static String removeHeader (String base64Content) {
        String[] baseStr = base64Content.split(";base64,");
        return baseStr[1];
    }

    /**
     * return a base64 content header if exists
     */
    public static String getBase64Header (String base64Content) {
        if (CheckBase64Tool.checkHeader(base64Content))
            return getHeader(base64Content);
        return null;
    }

    private static String getHeader (String base64Content) {
        String[] baseStr = base64Content.split(";base64,");
        return baseStr[0];
    }

    /**
     *  return a base64 data type from header if exists
     */
    public static String getBase64DataTypeFromHeader (String base64Content) {
        if (CheckBase64Tool.checkHeader(base64Content))
            return getDataType(base64Content);
        return null;
    }

    private static String getDataType (String base64Content) {
        String header = getHeader(base64Content);
        String[] types = header.split(":");
        return types[1];
    }


}

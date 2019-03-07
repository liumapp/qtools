package com.liumapp.qtools.file.zip;

import com.liumapp.qtools.file.basic.FileTool;
import com.liumapp.qtools.str.basic.StrTool;
import com.liumapp.qtools.str.suffix.SuffixTool;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * file ZipTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/3/7
 */
public class ZipTool {

    /**
     * using password to unzip file into the current folder
     * @param zipFilePath zip file path
     * @param password the password of zip file , if no password , pass null
     * @return the file array of zip file
     * @throws ZipException exception from zip4j
     */
    public static File[] unzip(String zipFilePath, String password) throws ZipException {
        File zipFile = new File(zipFilePath);
        File parentDir = zipFile.getParentFile();
        return unzip(zipFile, parentDir.getAbsolutePath(), password);
    }

    /**
     * using password to unzip file into the destPath , if the path not exist , then create it , illegal destPath will cause exception
     * @param zipFilePath the zip file path
     * @param destPath unzip file dest path
     * @param password the password of zip file, if no password, pass null
     * @return the file array of zip file
     * @throws ZipException exception from zip4j
     */
    public static File[] unzip(String zipFilePath, String destPath, String password) throws ZipException {
        File zipFile = new File(zipFilePath);

        return unzip(zipFile, destPath, password);
    }

    /**
     * using password to unzip file into the destPath , if the path not exist , then create it , illegal destPath will cause exception
     * @param zipFile the zip file object
     * @param destPath unzip file dest path
     * @param password the password of zip file, if no password, pass null
     * @return the file array of zip file
     * @throws ZipException exception from zip4j
     */
    @SuppressWarnings("unchecked")
    public static File[] unzip(File zipFile, String destPath, String password) throws ZipException {
        ZipFile zFile = new ZipFile(zipFile);
        zFile.setFileNameCharset("UTF-8");
        if (!zFile.isValidZipFile()) {
            throw new ZipException("Invalid zip files, it may be damaged");
        }

        File destDir = new File(destPath);
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }

        if (zFile.isEncrypted()) {
            if (StrTool.isSpace(password)) {
                throw new ZipException("Password can't be empty with encryption mode");
            }
            zFile.setPassword(password.toCharArray());
        }
        zFile.extractAll(destPath);

        List<FileHeader> headerList = zFile.getFileHeaders();
        List<File> extractedFileList = new ArrayList<File>();
        for (FileHeader fileHeader : headerList) {
            if (!fileHeader.isDirectory()) {
                extractedFileList.add(new File(destDir, fileHeader.getFileName()));
            }
        }

        File[] extractedFiles = new File[extractedFileList.size()];
        extractedFileList.toArray(extractedFiles);

        return extractedFiles;
    }

    /**
     * using password to unzip file into the current folder
     * @param srcPath the file path to zip
     * @param password the password to zip , if don't need, then pass null
     * @return the save path of zip file
     * @throws ZipException Exception from zip4j
     */
    public static String zip(String srcPath, String password) throws ZipException, IOException {
        return zip(srcPath, null, password);
    }

    /**
     * using password to zip file into the destPath folder
     * @param srcPath the file or folder to zip
     * @param destPath the save path of zip file
     * @param password the password to zip , if don't need, then pass null
     * @return the save path of zip file
     * @throws ZipException Exception from zip4j
     */
    public static String zip(String srcPath, String destPath, String password) throws ZipException, IOException {
        return zip(srcPath, destPath, true, password);
    }

    /**
     * using password to zip file or folder to the destPath folder, end with .zip
     * @param srcPath the file or folder to zip
     * @param destPath the save path of zip file, end with .zip
     * @param isCreateDir if the srcPath contains folder, pass true.
     * @param password the password to zip, if don't need, then pass null.
     * @return the save path of zip file
     * @throws ZipException Exception from zip4j
     */
    public static String zip(String srcPath, String destPath, boolean isCreateDir, String password) throws ZipException, IOException {
        File srcFile = new File(srcPath);
        FileTool.checkFilePath(destPath);
        if (!SuffixTool.checkStringHasSuffix(destPath)) {
            destPath = srcFile.getName() + ".zip";
        }
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        if (StrTool.isSpace(password)) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            parameters.setPassword(password.toCharArray());
        }

        ZipFile zipFile = new ZipFile(destPath);
        if (srcFile.isDirectory()) {
            if (!isCreateDir) {
                File[] subFiles = srcFile.listFiles();
                ArrayList<File> subFileList = new ArrayList<File>();
                Collections.addAll(subFileList, subFiles);
                zipFile.addFiles(subFileList, parameters);

                return destPath;
            }
            zipFile.addFolder(srcFile, parameters);
        } else {
            zipFile.addFile(srcFile, parameters);
        }

        return destPath;
    }

}

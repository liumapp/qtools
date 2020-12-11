package com.liumapp.qtools.file.ignores;

import com.liumapp.qtools.ignores.string.StrTool;
import java.io.*;
import java.util.*;

/**
 * author liumapp
 * file FileTool.java
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/8/7
 */
public class FileTool {

    /**
     * all static method , plz do not initialize the class
     */
    private FileTool() {
        throw new UnsupportedOperationException("not allowed to initialize");
    }

    /**
     * check the file path
     * if not found , than create file path
     * if can not create , than throw Error
     */
    public static boolean checkFilePath (String filePath) throws IOException {
        if (filePath == null) {
            return false;
        }

        File file = new File(filePath);

        if ( ! createDir(file.getParentFile())) {
            System.out.println("create file path failed , plz check the permission");
            return false;
        }

        return true;
    }

    public static void createDestFolder (String folderPath) {
        if (StrTool.isSpace(folderPath)) {
            return;
        }
        File destDir = null;
        if (folderPath.endsWith(File.separator)) {
            destDir = new File(folderPath);
        } else {
            destDir = new File(folderPath.substring(0, folderPath.lastIndexOf(File.separator)));
        }

        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    /**
     * write string to a new file
     * @param file File object
     * @param content String content
     * @return boolean true write success : false
     */
    public static boolean writeStringToNewFile (File file, String content) throws IOException {
        if (file == null || content == null) {
            return false;
        }
        if (!createFile(file)) {
            return false;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(content);
            return true;
        }
    }

    /**
     * create a file from input stream to savepath.
     */
    public static boolean createFileFromInputStream (InputStream is, String savePath) throws IOException {
        if (is == null || savePath == null)
            return false;
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        File targetFile = new File(savePath);
        createFile(targetFile);
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
        outStream.close();
        is.close();
        return true;
    }

    /**
     * read file contens from input stream
     * @param is input stream
     * @return file contents string
     * @throws IOException common io exception
     */
    public static String readFileFromInputStream (InputStream is) throws IOException {
        if (is == null )
            return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null)
        {
            sb.append(line + "\n");
        }
        reader.close();
        return sb.toString();
    }

    /**
     * ready byte array from input stream
     */
    public static byte[] readBytesFromInputStream (InputStream is) throws IOException {
        if (is == null)
            return null;
        byte[] result = new byte[is.available()];
        is.read(result);
        return result;
    }

    /**
     * create a file , and the directory needs .
     * @param file file object
     * @return true/false
     */
    public static boolean createFile (File file) throws IOException {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        return createDir(file.getParentFile()) && file.createNewFile();
    }

    /**
     * create a file , and the direcotory needs
     * @return File object
     */
    public static File createFileObject (String savePath, String saveName) throws IOException {
        File file = new File(savePath + "/" +saveName);
        createFile(file);
        return file;
    }

    /**
     * delete a file by file path
     * @param srcFilePath file save path
     * @return true: deleted success
     */
    public static boolean deleteFile (String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * delete a file
     * @param file file object
     * @return true : deleted success
     */
    public static boolean deleteFile (File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * create a file directory
     * @param file file object
     * @return true/false
     */
    public static boolean createDir (File file) throws IOException {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * delete dir
     * @param dirPath string dir path
     * @return {@code true}: delete success <br> {@code false}: delete failed
     */
    public static boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * delete dir
     * @param dir dir folder
     * @return {@code true}: delete success <br> {@code false}: delete failed
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        deleteFilesInDir(dir);
        return dir.delete();
    }

    /**
     * is file exists
     *
     * @param filePath file save path
     * @return {@code true}: exists <br> {@code false}: not exists
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * is file exists
     *
     * @param file file object
     * @return {@code true}: exists <br> {@code false}: not exists
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * get a file object by file savepath
     * @param filePath file savepath
     * @return file object
     */
    public static File getFileByPath(String filePath) {
        return StrTool.isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * is a direcotory according to a filepath.
     */
    public static boolean isDirectory (String filePath) {
        File file = new File(filePath);
        return file.isDirectory();
    }

    /**
     * delete all files in a specified folder
     * @param dir folder path
     * @return {@code true}: delete success <br> {@code false}: delete failed
     */
    public static boolean deleteFilesInDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Opens and reads a file, and returns the contents as one String.
     */
    public static String readFileAsString(String filename)
            throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null)
        {
            sb.append(line + "\n");
        }
        reader.close();
        return sb.toString();
    }

    /**
     * Open and read a file, and return the lines in the file as a list of
     * Strings.
     */
    public static List<String> readFileAsListOfStrings(String filename) throws Exception
    {
        List<String> records = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null)
        {
            records.add(line);
        }
        reader.close();
        return records;
    }

    /**
     * Reads a "properties" file, and returns it as a Map (a collection of key/value pairs).
     *
     * @param filename
     * @param delimiter
     * @return
     * @throws Exception
     */
    public static Map<String, String> readPropertiesFileAsMap(String filename, String delimiter)
            throws Exception
    {
        Map<String, String> map = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null)
        {
            if (line.trim().length()==0) continue;
            if (line.charAt(0)=='#') continue;
            // assumption here is that proper lines are like "String : http://xxx.yyy.zzz/foo/bar",
            // and the ":" is the delimiter
            int delimPosition = line.indexOf(delimiter);
            String key = line.substring(0, delimPosition-1).trim();
            String value = line.substring(delimPosition+1).trim();
            map.put(key, value);
        }
        reader.close();
        return map;
    }

    /**
     * Read a Java properties file and return it as a Properties object.
     */
    public static Properties readPropertiesFile(String canonicalFilename)
            throws IOException
    {
        Properties properties = new Properties();
        properties.load(new FileInputStream(canonicalFilename));
        return properties;
    }

    /**
     * Save the given text to the given filename.
     * @param canonicalFilename Like /Users/al/foo/bar.txt
     * @param text All the text you want to save to the file as one String.
     * @throws IOException
     */
    public static void writeFile(String canonicalFilename, String text)
            throws IOException
    {
        File file = new File (canonicalFilename);
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(text);
        out.close();
    }

    /**
     * Write an array of bytes to a file. Presumably this is binary data; for plain text
     * use the writeFile method.
     */
    public static void writeFileAsBytes(String fullPath, byte[] bytes) throws IOException
    {
        OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fullPath));
        InputStream inputStream = new ByteArrayInputStream(bytes);
        int token = -1;

        while((token = inputStream.read()) != -1)
        {
            bufferedOutputStream.write(token);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        inputStream.close();
    }

    public static void copyFile(File source, File destination) throws IOException
    {
        //if (!source.isFile() || !dest.isFile()) return false;

        byte[] buffer = new byte[100000];

        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try
        {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(source));
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destination));
            int size;
            while ((size = bufferedInputStream.read(buffer)) > -1)
            {
                bufferedOutputStream.write(buffer, 0, size);
            }
        }
        catch (IOException e)
        {
            // TODO may want to do something more here
            throw e;
        }
        finally
        {
            try
            {
                if (bufferedInputStream != null)
                {
                    bufferedInputStream.close();
                }
                if (bufferedOutputStream != null)
                {
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
            }
            catch (IOException ioe)
            {
                // TODO may want to do something more here
                throw ioe;
            }
        }
    }

}

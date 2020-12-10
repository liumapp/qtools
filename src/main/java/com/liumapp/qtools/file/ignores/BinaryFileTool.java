package com.liumapp.qtools.file.ignores;

import java.io.*;
import java.util.Base64;

/**
 * file BinaryFileTool.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/10
 */
public class BinaryFileTool {

    /**
     * read a file
     * return bytes array
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int)file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                int bufSize = 1024;
                byte[] buffer = new byte[bufSize];

                int len;
                while(-1 != (len = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }

    /**
     * write a byte array to file system
     */
    public static void writeBytesToFileSystem(byte[] data, String output) throws IOException {
        DataOutputStream out = null;

        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
            out.write(data);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * convert base64 string to binary bytes
     */
    public static byte[] Base64ToBinaryBytes (String base64) {
        byte[] decode =  Base64.getDecoder().decode(base64);
        return decode;
    }


    /**
     * convert binary bytes to hex bytex
     */
    public static byte[] convertBinaryByteToHexByte (byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().getBytes();
    }

}

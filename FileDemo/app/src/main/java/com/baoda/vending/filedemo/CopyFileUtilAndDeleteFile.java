package com.baoda.vending.filedemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author by AllenJ on 2019/3/1.
 */


public class CopyFileUtilAndDeleteFile {

    /**
     * 复制旧文件夹文件，粘贴到新文件夹下
     * @param newFilePath 新文件地址 /storage/Android/new/newFile.txt
     * @param oldFilePath 旧文件地址 /storage/Android/old/oldFile.txt
     */
    public static void copy(String newFilePath, String oldFilePath) {
        try {
            FileInputStream inputStream = new FileInputStream(oldFilePath);
            FileOutputStream outputStream = new FileOutputStream(newFilePath);
            byte[] buffer = new byte[1024];
            int length;
            while (-1 != (length = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除指定文件夹下的所有文件
     * @param directoryFile 指定文件夹 /storage/Android/directory
     */
    public static void deleteDirectoryFile(File directoryFile) {
        if (directoryFile.isDirectory()) {
            File[] files = directoryFile.listFiles();
            for (File i : files) {
                if (i.isFile()) {
                    i.delete();
                }
            }
        }
    }
}

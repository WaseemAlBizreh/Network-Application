package com.networkapplication.FileStorage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePath {

    public static String desktopPath = System.getProperty("user.home") + "/Desktop\\Network-Project";

    public static File createFile(String fileName, Long group_id) {
        File file = new File(desktopPath);
        if (!file.exists())
            new java.io.File(desktopPath).mkdirs();
        File file1 = new java.io.File(desktopPath + "\\group" + group_id);
        if (!file1.exists())
            new java.io.File(desktopPath + "\\group" + group_id).mkdirs();
        String filePath = desktopPath + "\\group" + group_id + "\\" + fileName;
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
        } else {
            try {
                Files.createFile(path);
            } catch (Exception e) {
                System.err.println("Error creating the file: " + e.getMessage());
            }
        }
        return new File(filePath);
    }
}


package com.networkapplication.FileStorage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class FileStorageManager {


    @Value("${document.uploadDirectory}")
    private String uploadDirectory;


    @Async
    public void save(MultipartFile file, Long group_id) throws IOException {
        String fileName = file.getOriginalFilename();
        boolean directory = new File(uploadDirectory + "\\group" + group_id).mkdirs();
        File newFile = new File(uploadDirectory + "\\group" + group_id + File.separator + fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();
    }
}

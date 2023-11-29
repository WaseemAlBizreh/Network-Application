package com.networkapplication.services;
import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.networkapplication.models.File;
import com.networkapplication.repositories.FileRepository;

@Service
public class FileServiceImp {

    @Autowired
    private FileRepository fileDBRepository;

    public File store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File File = new File();
        File.setFileName(fileName);
        File.setContent(file.getBytes());

        return fileDBRepository.save(File);
    }

    public File getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<File> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
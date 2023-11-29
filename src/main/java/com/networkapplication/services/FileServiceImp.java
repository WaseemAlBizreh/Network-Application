package com.networkapplication.services;
import java.io.IOException;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.networkapplication.models.File;
import com.networkapplication.repositories.FileRepository;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService{


    private FileRepository fileDBRepository;

    public File getFile(Long id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<File> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    @Override
    public ResponseEntity addFile() {
        return null;
    }

    @Override
    public ResponseEntity getFile() {
        return null;
    }
}
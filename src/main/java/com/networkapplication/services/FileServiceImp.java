package com.networkapplication.services;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public MessageDTO fileUpload(FileDTORequest request) {
        try {
            Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String fileName = request.getFile().getOriginalFilename();

            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(request.getFile().getInputStream(),
                    targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return MessageDTO.builder()
                    .message("File uploaded successfully!")
                    .build();
        } catch (IOException e) {
            return MessageDTO.builder()
                    .message("Failed to upload file.")
                    .build();
        }
    }

    @Override
    public ResponseEntity getFile() {
        return null;
    }
}
package com.networkapplication.services;

import com.networkapplication.config.JwtService;
import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.exceptions.StorageException;
import com.networkapplication.models.File;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.FileRepository;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @NonNull
    HttpServletRequest Request;
    UserRepository userRepository;
    GroupRepository groupRepository;
    FileRepository fileRepository;
    private final JwtService jwtService;

    @Override
    public MessageDTO fileUpload(FileDTORequest request) {
        //Get User
        String header = Request.getHeader("Authorization");
        String token = header.substring(7);
        User user = userRepository.findUserByUsername(jwtService.extractUsername(token))
                .orElseThrow(() -> new NoSuchElementException("No User Found"));

        //Get Group
        Group group = groupRepository.findById(request.getGroup_id())
                .orElseThrow(() -> new NoSuchElementException("No group Found"));

        //Save File to Server
        try {
            if (request.getFile().isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String fileName = request.getFile().getOriginalFilename();

            if (fileName == null) {
                throw new IOException("File Name doesn't Exist");
            }

            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(request.getFile().getInputStream(),
                    targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Set File Record to DB
            File file = new File();
            file.setFileName(fileName);
            file.setGroupFiles(group);
            file.setOwnerFile(user);
            if (group.getFile() != null) {
                List<File> files = group.getFile();
                files.add(file);
                group.setFile(files);
            } else {
                group.setFile(List.of(file));
            }
            if (user.getFiles() != null) {
                List<File> files = user.getFiles();
                files.add(file);
                user.setFiles(files);
            } else {
                user.setFiles(List.of(file));
            }
            userRepository.save(user);
            groupRepository.save(group);
            fileRepository.save(file);
        } catch (IOException | StorageException e) {
            return MessageDTO.builder()
                    .message(e.getMessage())
                    .build();
        }
        return MessageDTO.builder()
                .message("File Upload Successfully")
                .build();
    }

    @Override
    public FileDTOResponse loadFile(Long fileId) {
        //Get FileName From DB
        String fileName = "fileName";

        //Get File Form Server
        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
        Path fileLocation = uploadPath.resolve(fileName);
        return null;
    }
}
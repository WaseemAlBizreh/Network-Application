package com.networkapplication.services;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.config.JwtService;
import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.MessageDTO;
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
public class FileServiceImp implements FileService{
    @NonNull
    HttpServletRequest Request;
    UserRepository userRepository;
    GroupRepository groupRepository;
    FileRepository fileRepository;
    private final JwtService jwtService;

    @Override
    public MessageDTO addFile(FileDTORequest fileDTORequest) {
        String header= Request.getHeader("Authorization");
        String token=header.substring(7);
        User user = userRepository.findUserByUsername(jwtService.extractUsername(token))
                .orElseThrow(() -> new NoSuchElementException("No User Found"));
        Group group=groupRepository.findById(fileDTORequest.getGroup_id())
                    .orElseThrow(() -> new NoSuchElementException("No group Found"));
        File file=new File();
        file.setFileName(fileDTORequest.getFileName());
        file.setGroupFiles(group);
        file.setOwnerFile(user);
        if (group.getFile()!=null){
          List<File> files= group.getFile();
          files.add(file);
           group.setFile(files);
        }
        else {
            group.setFile(List.of(file));
        }
        if (user.getFiles()!=null){
            List<File> files= user.getFiles();
            files.add(file);
            user.setFiles(files);
        }
        else {
            user.setFiles(List.of(file));
        }
        userRepository.save(user);
        groupRepository.save(group);
        fileRepository.save(file);
    return MessageDTO.builder().message("file upload successfully").build();
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
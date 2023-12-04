package com.networkapplication.services;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.File;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.FileRepository;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final FileRepository fileRepository;
    private final Utils utils;

    @Override
    public FileDTOResponse fileUpload(FileDTORequest request) {
        String fileName;
        Path targetLocation;
        File file;
        Resource urlResource;
        String contentType;

        //Get User
        User user = utils.getCurrentUser();

        //Get Group
        Group group = groupRepository.findById(request.getGroup_id())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        //Save File to Server
        try {
            if (request.getFile().isEmpty()) {
                throw new IllegalArgumentException("Failed to store empty file.");
            }
            Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            fileName = request.getFile().getOriginalFilename();

            if (fileName == null) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                        "File Name doesn't Exist");
            }
            fileName = fileName.replaceAll(" ", "_");

            //todo: check file exist

            targetLocation = uploadPath.resolve(fileName);
            Files.copy(request.getFile().getInputStream(),
                    targetLocation, StandardCopyOption.REPLACE_EXISTING);

            urlResource = new UrlResource(targetLocation.toUri());
            // Set File Record to DB
            file = File.builder()
                    .fileName(fileName)
                    .ownerFile(user)
                    .groupFiles(group)
                    .build();
            if (group.getFile() != null) {
                group.getFile().add(file);
            } else {
                group.setFile(List.of(file));
            }
            if (user.getFiles() != null) {
                user.getFiles().add(file);
            } else {
                user.setFiles(List.of(file));
            }
            userRepository.save(user);
            groupRepository.save(group);
            fileRepository.save(file);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(500),
                    e.getMessage());
        }
        //TODO: not this path
        return FileDTOResponse.builder()
                .file_id(file.getId())
                .file_name(fileName)
                .path(urlResource)
                .message("File Uploaded Successfully")
                .build();
    }

    @Override
    public FileDTOResponse loadFile(Long fileId) {
        //Get FileName From DB
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new NoSuchElementException("No User Found"));

        //Get User
        User user = utils.getCurrentUser();

        //Get Group
        //Check if User is a member in filesGroup
        Group group = file.getGroupFiles();
        List<User> members = group.getMembers();
        if (!members.contains(user)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403),
                    "You are not a member of this file's group");
        }

        //Get File Form Server
        String fileName = file.getFileName();
        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName);
        Resource resource = new org.springframework.core.io.PathResource(filePath);
        if (resource.exists()) {
            return FileDTOResponse.builder()
                    .file_id(fileId)
                    .file_name(fileName)
                    .path(resource)
                    .message("Success")
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                    "File Not Found");
        }
    }

    @Override
    public void deleteAllInGroup(Long group_id) {

    }

    @Override
    public List<File> loadAllGroupFiles(Long groupId) {
        return null;
    }
}
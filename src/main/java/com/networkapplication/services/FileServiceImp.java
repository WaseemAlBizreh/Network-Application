package com.networkapplication.services;

import com.networkapplication.dtos.Request.CheckInDTO;
import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.GroupFilesDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.File;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.FileRepository;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {
    @Value("${document.uploadDirectory}")
    private String uploadDirectory;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final FileRepository fileRepository;
    private final Utils utils;
//
//    @Override
//    public FileDTOResponse fileUpload(FileDTORequest request) throws ResponseException {
//        String fileName;
//        Path targetLocation;
//        File file;
//        Resource urlResource;
//        String contentType;
//
//        //Get User
//        User user = utils.getCurrentUser();
//
//        //Get Group
//        Group group = groupRepository.findById(request.getGroup_id())
//                .orElseThrow(() -> new ResponseException(404,"Group not found"));
//        //get file name
//        fileName = request.getFile().getOriginalFilename();
//        //Save File to Server
//        try {
//            if (request.getFile().isEmpty()) {
//                throw new ResponseException(404,"Failed to store empty file.");
//            }
//            Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
//            Files.createDirectories(uploadPath);
//            if (fileName == null) {
//                throw new ResponseException(404,
//                        "File Name doesn't Exist");
//            }
//            fileName = fileName.replaceAll(" ", "_");
//
//            //todo: check file exist
//
//            targetLocation = uploadPath.resolve(fileName);
//            Files.copy(request.getFile().getInputStream(),
//                    targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            urlResource = new UrlResource(targetLocation.toUri());
//            // Set File Record to DB
//            file = File.builder()
//                    .fileName(fileName)
//                    .ownerFile(user)
//                    .groupFiles(group)
//                    .build();
//            if (group.getFile() != null) {
//                group.getFile().add(file);
//            } else {
//                group.setFile(List.of(file));
//            }
//            if (user.getFiles() != null) {
//                user.getFiles().add(file);
//            } else {
//                user.setFiles(List.of(file));
//            }
//            userRepository.save(user);
//            groupRepository.save(group);
//            fileRepository.save(file);
//
//        }
//        // TODO: 12/4/2023 Exception because IO
//        catch (Exception ex) {
//            throw new ResponseException(500,
//                    ex.getMessage());
//        }
//        //TODO: not this path
//        return FileDTOResponse.builder()
//                .file_id(file.getId())
//                .file_name(fileName)
//                .path(urlResource)
//                .message("File Uploaded Successfully")
//                .build();
//    }
//
//    @Override
//    public FileDTOResponse loadFile(Long fileId)throws ResponseException {
//        //Get FileName From DB
//        File file = fileRepository.findById(fileId)
//                .orElseThrow(() -> new ResponseException(404,"No User Found"));
//
//        //Get User
//        User user = utils.getCurrentUser();
//
//        //Get Group
//        //Check if User is a member in filesGroup
//        Group group = file.getGroupFiles();
//        List<User> members = group.getMembers();
//        if (!members.contains(user)) {
//            throw new ResponseException(403,
//                    "You are not a member of this file's group");
//        }
//
//        //Get File Form Server
//        String fileName = file.getFileName();
//        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
//        Path filePath = uploadPath.resolve(fileName);
//        Resource resource = new org.springframework.core.io.PathResource(filePath);
//        if (resource.exists()) {
//            return FileDTOResponse.builder()
//                    .file_id(fileId)
//                    .file_name(fileName)
//                    .path(resource)
//                    .message("Success")
//                    .build();
//        } else {
//            throw new ResponseException(404,
//                    "File Not Found");
//        }
//    }

    @Override
    public MessageDTO deleteAllInGroup(Long group_id) throws ResponseException {
        Group group = groupRepository.findById(group_id).orElseThrow(
                () -> new ResponseException(404, "No Group Found")
        );
        List<File> files = group.getFile();
        fileRepository.deleteAll(files);
        return MessageDTO.builder().message("Delete All Files").build();
    }

    @Override
    public List<GroupFilesDTOResponse> loadAllGroupFiles(Long groupId) throws ResponseException {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new ResponseException(404, "Group not found"));
        List<GroupFilesDTOResponse> filesDTOGroupResponses = new ArrayList<>();
        for (File file :
                group.getFile()) {
            filesDTOGroupResponses.add(new GroupFilesDTOResponse(file));
        }

        return filesDTOGroupResponses;
    }

    @Override
    public MessageDTO saveFile(MultipartFile file) throws IOException, ResponseException {
        User user = utils.getCurrentUser();
        Group group = groupRepository.findById(102L).orElseThrow(
                () -> new ResponseException(404, "notFound")
        );
        if (fileRepository.findFileByUsername(file.getName()).isPresent()) {
            throw new ResponseException(401, "The File Name already exists");
        }
        if (group.getMembers().contains(user)) {
            File textFile = File.builder()
                    .fileName(file.getOriginalFilename())
                    .groupFiles(group).ownerFile(user)
                    .lastEditDate(LocalDate.of(2020, 1, 4)).build();
            fileRepository.save(textFile);
            groupRepository.save(group);
            userRepository.save(user);
            return MessageDTO.builder().message("file upload").build();
        } else {
            return MessageDTO.builder().message("you are not found in group").build();
        }
    }

    @Override
    public FileDTOResponse getFile(FileDTORequest fileDTORequest) throws ResponseException {
        System.out.println(uploadDirectory);
        java.io.File folder = new java.io.File(uploadDirectory);
        User user = utils.getCurrentUser();
        Group group = groupRepository.findById(fileDTORequest.getGroup_id()).orElseThrow(
                () -> new ResponseException(404, "GroupNotFound")
        );
        if (!group.getMembers().contains(user)) {
            throw new ResponseException(401, "unAuthorized");
        }
        File file = fileRepository.findFileByUsername(fileDTORequest.getFile_name()).orElseThrow(
                () -> new ResponseException(404, "Not Found File")
        );
        System.out.println(file.getPath());
        java.io.File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (java.io.File file1 : listOfFiles) {
            if (file1.getName().equals(fileDTORequest.getFile_name())) {
                System.out.println(file.getPath());
                return FileDTOResponse.builder()
                        .file_id(file.getId())
                        .file_name(fileDTORequest.getFile_name())
                        .path(file.getPath())
                        .message("Success").build();
            }
        }
        return FileDTOResponse.builder().message("File Not Found In Folder").build();
    }

    @Override
    public MessageDTO checkIn(CheckInDTO checkIn) throws ResponseException {
        User user = utils.getCurrentUser();
        File file = fileRepository.findById(checkIn.getFile_id()).orElseThrow(() ->
                new ResponseException(404, "Not Found"));
        if (file.getGroupFiles().getMembers().contains(user)) {
            if (file.getCheckin() != null) {
                throw new ResponseException(403, "File is CheckIN");
            } else {
                file.setCheckin(user);
                fileRepository.save(file);
                userRepository.save(user);
                return MessageDTO.builder().message("Success").build();
            }
        } else {
            throw new ResponseException(403, "you are not found in group");
        }
    }

    @Override
    public MessageDTO checkOut(CheckInDTO checkOut) throws ResponseException {
        User user = utils.getCurrentUser();
        File file = fileRepository.findById(checkOut.getFile_id()).orElseThrow(() ->
                new ResponseException(404, "File Not Found"));
        if (file.getGroupFiles().getMembers().contains(user)) {
            if (file.getCheckin().getId().equals(user.getId()) && file.getCheckin() != null) {
                file.setCheckin(null);
                fileRepository.save(file);
                userRepository.save(user);
                return MessageDTO.builder().message("success").build();
            } else {
                throw new ResponseException(401, "unAuthorized");
            }
        } else
            throw new ResponseException(403, "you are not found in group");
    }
}
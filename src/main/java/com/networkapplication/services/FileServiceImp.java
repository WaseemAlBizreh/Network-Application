package com.networkapplication.services;

import com.networkapplication.FileStorage.FileStorageManager;
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
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {
    @Value("${document.uploadDirectory}")
    private String uploadDirectory;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final FileRepository fileRepository;
    private final Utils utils;
    private final FileStorageManager fileStorageManager;

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
    public MessageDTO createFile(MultipartFile file,Long group_id) throws IOException, ResponseException {
        User user = utils.getCurrentUser();
        Group group = groupRepository.findById(group_id).orElseThrow(
                () -> new ResponseException(404, "Group notFound")
        );
        for (int i = 0; i < group.getFile().size(); i++) {
            if(Objects.equals(file.getOriginalFilename(), group.getFile().get(i).getFileName())){
                throw new ResponseException(422,"File Name is already Taken");
            }
        }
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
    public MessageDTO uploadFile(FileDTORequest fileDTORequest) throws ResponseException,IOException {
        java.io.File folder = new java.io.File(uploadDirectory+"group "+fileDTORequest.getGroup_id());
        java.io.File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        User user=utils.getCurrentUser();
        Group group = groupRepository.findById(fileDTORequest.getGroup_id()).orElseThrow(
                () -> new ResponseException(404, "Group notFound"
                ));
        File file=null;
        if (group.getFile()!=null)
        for (int i = 0; i < group.getFile().size(); i++) {
            if (group.getFile().get(i).getCheckin().equals(user) &&
                group.getFile().get(i).getFileName().equals(fileDTORequest.getFile().getOriginalFilename())){
                file=group.getFile().get(i);
                break;
            }
        }
        if (file==null)
            throw new ResponseException(422,"there is no such file checked in by this name");
        for (java.io.File file1 : listOfFiles) {
           if(fileDTORequest.getFile().getOriginalFilename().equals(file1.getName())){
                fileStorageManager.save(fileDTORequest.getFile(),fileDTORequest.getGroup_id());
                return MessageDTO.builder().message("File Updated Successfully").build();
           }
        }
        throw new ResponseException(404,"File Not Found ");
    }

    @Override
    public FileDTOResponse getFile(FileDTORequest fileDTORequest) throws ResponseException {
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
        java.io.File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (java.io.File file1 : listOfFiles) {
            if (file1.getName().equals(fileDTORequest.getFile_name())) {
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
    public synchronized MessageDTO checkIn(CheckInDTO checkIn) throws ResponseException {
        User user = utils.getCurrentUser();
        for (int i = 0; i < checkIn.getFile_id().size(); i++) {
            File file = fileRepository.findById(checkIn.getFile_id().get(i)).orElseThrow(() ->
                    new ResponseException(404, "File Not Found"));
            if (file.getGroupFiles().getMembers().contains(user)) {
                if (file.getCheckin() != null) {
                    throw new ResponseException(403, file.getFileName() + " is CheckIN");
                }
            } else {
                throw new ResponseException(403, "you are not found in group");
            }
        }
        for (int j = 0; j < checkIn.getFile_id().size(); j++) {
            File file = fileRepository.findById(checkIn.getFile_id().get(j)).orElseThrow();
            file.setCheckin(user);
            fileRepository.save(file);
            userRepository.save(user);
        }
        return MessageDTO.builder().message("CheckIn Success").build();
    }

    @Override
    public MessageDTO checkOut(CheckInDTO checkOut) throws ResponseException {
        User user = utils.getCurrentUser();
        File file = fileRepository.findById(checkOut.getFile_id().get(0)).orElseThrow(() ->
                new ResponseException(404, "File Not Found"));
        if (file.getGroupFiles().getMembers().contains(user)) {
            if (file.getCheckin() != null){
            if (file.getCheckin().getId().equals(user.getId()) ) {
                file.setCheckin(null);
                fileRepository.save(file);
                userRepository.save(user);
                return MessageDTO.builder().message("File Checked Out Successfully").build();
            } else {
                throw new ResponseException(401, "unAuthorized");
            }} else {
                throw new ResponseException(400,"File is already checked out");
            }
        } else
            throw new ResponseException(403, "you are not found in group");
    }
}
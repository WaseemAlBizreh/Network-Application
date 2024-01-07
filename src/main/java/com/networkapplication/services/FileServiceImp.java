package com.networkapplication.services;

import com.networkapplication.FileStorage.FilePath;
import com.networkapplication.dtos.Request.CheckInDTO;
import com.networkapplication.dtos.Request.FileName;
import com.networkapplication.dtos.Response.GroupFilesDTOResponse;
import com.networkapplication.dtos.Response.ListGroupFilesDTO;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.models.File;
import com.networkapplication.models.Group;
import com.networkapplication.models.User;
import com.networkapplication.repositories.FileRepository;
import com.networkapplication.repositories.GroupRepository;
import com.networkapplication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {
    @Value("${document.uploadDirectory}")
    private String uploadDirectory;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final FileRepository fileRepository;
    private final Utils utils;
    private static final ConcurrentHashMap<Long, Object> locks = new ConcurrentHashMap<>();

    @Transactional
    @Override
    public MessageDTO deleteAllFilesInGroup(Long group_id) throws ResponseException {

        User user = utils.getCurrentUser();
        Group group = groupRepository.findById(group_id).orElseThrow(
                () -> new ResponseException(404, "No Group Found")
        );
        if (user.getId().equals(group.getAdmin().getId())) {
            List<File> files = group.getFile();
            fileRepository.deleteAll(files);
            return MessageDTO.builder().message("Delete All Files").build();
        } else {
            throw new ResponseException(403, "unAuthorized");
        }
    }

    @Override
    public ListGroupFilesDTO loadAllGroupFiles(Long groupId) throws ResponseException {
        User user = utils.getCurrentUser();
        boolean status = false;
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new ResponseException(404, "Group not found"));
        if (group.getMembers().contains(user)) {
            List<GroupFilesDTOResponse> filesDTOGroupResponses = new ArrayList<>();
            ListGroupFilesDTO listGroupFilesDTO = new ListGroupFilesDTO();
            for (File file :
                    group.getFile()) {
                if (file.getCheckin() != null) {
                    if (Objects.equals(user.getId(), file.getCheckin().getId()))
                        status = true;
                } else
                    status = false;
                filesDTOGroupResponses.add(new GroupFilesDTOResponse(file, status));
            }
            listGroupFilesDTO.setGroupFilesDTOResponses(filesDTOGroupResponses);
            return listGroupFilesDTO;
        } else {
            throw new ResponseException(403, "You are not in the group");
        }
    }

    @Transactional
    @Override
    public MessageDTO createFile(MultipartFile file, Long group_id) throws IOException, ResponseException {
        User user = utils.getCurrentUser();
        Group group = groupRepository.findById(group_id).orElseThrow(
                () -> new ResponseException(404, "Group notFound")
        );
        if (group.getMembers().contains(user)) {
            for (int i = 0; i < group.getFile().size(); i++) {
                if (Objects.equals(file.getOriginalFilename() + group_id, group.getFile().get(i).getFileName())) {
                    throw new ResponseException(422, "File Name is already Taken");
                }
            }
            if (fileRepository.findFileByUsername(file.getName() + group_id).isPresent()) {
                throw new ResponseException(401, "The File Name already exists");
            }
            File textFile = File.builder()
                    .fileName(file.getOriginalFilename() + group_id)
                    .path(System.getProperty("user.home") + "/Desktop\\Network-Project" + "\\group" + group_id)
                    .groupFiles(group).ownerFile(user)
                    .lastEditDate(LocalDate.now()).build();
            fileRepository.save(textFile);
            groupRepository.save(group);
            userRepository.save(user);
            String fileName = file.getOriginalFilename();
            java.io.File newFile = FilePath.createFile(fileName, group_id);
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(file.getBytes());
            bufferedOutputStream.close();
            return MessageDTO.builder().message("file upload").build();
        } else {
            throw new ResponseException(403, "you are not a member in this group");
        }
    }


    @Override
    public ResponseEntity<Resource> getFile(Long group_id, FileName file_name) throws ResponseException {

        java.io.File folder = new java.io.File(System.getProperty("user.home") + "/Desktop\\Network-Project\\group" + group_id);
        User user = utils.getCurrentUser();
        Group group = groupRepository.findById(group_id).orElseThrow(
                () -> new ResponseException(404, "GroupNotFound")
        );
        if (!group.getMembers().contains(user)) {
            throw new ResponseException(401, "unAuthorized");
        }

        File file = fileRepository.findFileByUsername(file_name.getName() + group_id).orElseThrow(
                () -> new ResponseException(404, "Not Found File")
        );
        java.io.File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (java.io.File file1 : listOfFiles) {
            if (file1.getName().equals(file_name.getName())) {
                String filePath = System.getProperty("user.home") + "/Desktop\\Network-Project\\group" + group_id + "\\" + file_name.getName();
                Resource resource = new FileSystemResource(filePath);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file");
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.ALL))
                        .body(resource);

            }
        }
        throw new ResponseException(404, "File Not Found");
    }


    @Override
    public MessageDTO updateFile(MultipartFile file1, Long group_id) throws ResponseException, IOException {
        java.io.File folder = new java.io.File(System.getProperty("user.home") + "/Desktop\\Network-Project" + "\\group" + group_id);
        java.io.File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        User user = utils.getCurrentUser();
        Group group = groupRepository.findById(group_id).orElseThrow(
                () -> new ResponseException(404, "Group notFound"
                ));
        File file = null;
        if (group.getFile() != null)
            for (int i = 0; i < group.getFile().size(); i++) {
                if (group.getFile().get(i).getCheckin() != null) {
                    if (group.getFile().get(i).getCheckin().equals(user) &&
                            group.getFile().get(i).getFileName().replace(group_id.toString(), "").equals(file1.getOriginalFilename())) {
                        file = group.getFile().get(i);
                        break;
                    }
                }
            }
        if (file == null)
            throw new ResponseException(422, "there is no such file checked in by this name");
        for (java.io.File file2 : listOfFiles) {
            if (file1.getOriginalFilename().equals(file2.getName())) {
                String fileName = file1.getOriginalFilename();

                java.io.File newFile = FilePath.createFile(fileName, group_id);
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                bufferedOutputStream.write(file1.getBytes());
                bufferedOutputStream.close();
                return MessageDTO.builder().message("File Updated Successfully").build();
            }
        }
        throw new ResponseException(404, "File Not Found ");
    }

    @Transactional
    @Override
    public MessageDTO checkIn(CheckInDTO checkIn) throws ResponseException {

        User user = utils.getCurrentUser();
        if (checkIn.getFile_id() == null) {
            checkIn.setFile_id(List.of());
        }

        for (int h = 0; h < checkIn.getFile_id().size(); h++) {
            Object lock = locks.computeIfAbsent(checkIn.getFile_id().get(h), k -> new Object());
            synchronized (lock) {
                for (int i = 0; i < checkIn.getFile_id().size(); i++) {

                    File file = fileRepository.findById(checkIn.getFile_id().get(i)).orElseThrow(() ->
                            new ResponseException(404, "File Not Found"));
                    if (file.getGroupFiles().getMembers().contains(user)) {
                        if (file.getCheckin() != null) {
                            throw new ResponseException(403, file.getFileName() + " is CheckIN");
                        }
                    } else {
                        return MessageDTO.builder().message("you are not found in group").build();
                    }
                }
                if (user.getMyFiles() == null) {
                    user.setMyFiles(List.of());
                }
                for (int j = 0; j < checkIn.getFile_id().size(); j++) {
                    File file = fileRepository.findById(checkIn.getFile_id().get(j)).orElseThrow();
                    file.setCheckin(user);
                    user.getMyFiles().add(file);
                    fileRepository.save(file);
                    userRepository.save(user);
                    Timer timer = new Timer("FileCheckInTimer");
                    long delayInMillis = 3 * 60 * 60 * 1000; // تعديل الوقت حسب الحاجة (3 أيام)

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            file.setCheckin(null);
                            fileRepository.save(file);
                            userRepository.save(user);
                        }
                    }, new Date(System.currentTimeMillis() + delayInMillis));
                }
            }
        }
        return MessageDTO.builder().message("CheckIn Success").build();
    }

    @Transactional
    @Override
    public MessageDTO checkOut(CheckInDTO checkOut) throws ResponseException {
        User user = utils.getCurrentUser();
        if (checkOut.getFile_id() == null) {
            checkOut.setFile_id(List.of());
        }
        for (int i = 0; i < checkOut.getFile_id().size(); i++) {
            File file = fileRepository.findById(checkOut.getFile_id().get(i)).orElseThrow(() ->
                    new ResponseException(404, "File Not Found"));
            if (file.getGroupFiles().getMembers().contains(user)) {
                if (file.getCheckin() != null) {
                    if (file.getCheckin().getId().equals(user.getId())) {
                        file.setCheckin(null);
                        fileRepository.save(file);
                        userRepository.save(user);
                    } else {
                        throw new ResponseException(401, "unAuthorized");
                    }
                } else {
                    throw new ResponseException(400, "File is already checked out");
                }
            } else
                throw new ResponseException(403, "you are not found in group");
        }
        return MessageDTO.builder().message("File Checked Out Successfully").build();
    }

    @Transactional
    @Override
    public MessageDTO deleteFile(Long groupId, CheckInDTO filesId) throws ResponseException {
//      get user or admin
        User user = utils.getCurrentUser();
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new ResponseException(404, "Not Found Group")
        );
        if (group.getMembers().contains(user)) {
            for (int i = 0; i < filesId.getFile_id().size(); i++) {
                File file = fileRepository.findById(filesId.getFile_id().get(i)).orElseThrow(
                        () -> new ResponseException(404, "Not Found File")
                );
                if (group.getFile().contains(file) && (file.getOwnerFile().getId().equals(user.getId())
                        || user.getId().equals(group.getAdmin().getId()))) {
                    fileRepository.delete(file);
                    userRepository.save(user);
                }
            }
        } else {
            return MessageDTO.builder().message("You are not in the group").build();
        }
        return MessageDTO.builder().message("Files Deleted Successfully").build();
    }
}
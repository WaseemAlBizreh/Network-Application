package com.networkapplication.services;

import com.networkapplication.dtos.Request.CheckInDTO;
import com.networkapplication.dtos.Request.FileName;
import com.networkapplication.dtos.Response.*;
import com.networkapplication.exceptions.ResponseException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {


    MessageDTO deleteAllFilesInGroup(Long group_id) throws ResponseException;

    ListGroupFilesDTO loadAllGroupFiles(Long groupId) throws ResponseException;

    CreateFileDTOResponse createFile(MultipartFile file, Long group_id) throws IOException, ResponseException;

    ResponseEntity<Resource> getFile(Long group_id, FileName file_name) throws ResponseException;

    MessageDTO updateFile(MultipartFile file, Long group_id) throws ResponseException, IOException;

    MessageDTO checkIn(CheckInDTO checkIn) throws ResponseException;

    MessageDTO checkOut(CheckInDTO checkIn) throws ResponseException;

    MessageDTO deleteFile(Long groupId,CheckInDTO filesId) throws ResponseException;
}

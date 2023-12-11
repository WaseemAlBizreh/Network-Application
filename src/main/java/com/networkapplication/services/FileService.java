package com.networkapplication.services;

import com.networkapplication.dtos.Request.CheckInDTO;
import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.GroupFilesDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.exceptions.ResponseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
//    FileDTOResponse fileUpload(FileDTORequest request) throws ResponseException;
//
//    FileDTOResponse loadFile(Long id) throws ResponseException;

    MessageDTO deleteAllInGroup(Long group_id) throws ResponseException;

    List<GroupFilesDTOResponse> loadAllGroupFiles(Long groupId) throws ResponseException;

    MessageDTO createFile(MultipartFile file,Long group_id) throws IOException, ResponseException;

    FileDTOResponse getFile(FileDTORequest fileDTORequest) throws ResponseException;

    MessageDTO uploadFile(FileDTORequest fileDTORequest) throws ResponseException, IOException;

    MessageDTO checkIn(CheckInDTO checkIn) throws ResponseException;

    MessageDTO checkOut(CheckInDTO checkIn) throws ResponseException;
}

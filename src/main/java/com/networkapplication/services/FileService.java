package com.networkapplication.services;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.GroupFilesDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.exceptions.ResponseException;

import java.util.List;

public interface FileService {
    FileDTOResponse fileUpload(FileDTORequest request) throws ResponseException;

    FileDTOResponse loadFile(Long id) throws ResponseException;

    MessageDTO deleteAllInGroup(Long group_id) throws ResponseException;

    List<GroupFilesDTOResponse> loadAllGroupFiles(Long groupId) throws ResponseException;
}

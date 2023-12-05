package com.networkapplication.services;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.GroupFilesDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;

import java.util.List;

public interface FileService {
    FileDTOResponse fileUpload(FileDTORequest request);

    FileDTOResponse loadFile(Long id);

    MessageDTO deleteAllInGroup(Long group_id);

    List<GroupFilesDTOResponse> loadAllGroupFiles(Long groupId);
}

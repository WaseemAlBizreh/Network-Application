package com.networkapplication.services;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.models.File;

import java.util.List;

public interface FileService {
    FileDTOResponse fileUpload(FileDTORequest request);

    FileDTOResponse loadFile(Long id);
    void deleteAllInGroup(Long group_id);
    List<File> loadAllGroupFiles(Long groupId);
}

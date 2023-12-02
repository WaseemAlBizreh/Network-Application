package com.networkapplication.services;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.MessageDTO;
import org.springframework.http.ResponseEntity;

public interface FileService {


    MessageDTO addFile(FileDTORequest fileDTORequest);

    ResponseEntity getFile() ;
}

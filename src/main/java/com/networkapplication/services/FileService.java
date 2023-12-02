package com.networkapplication.services;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.MessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface FileService {
     MessageDTO fileUpload(FileDTORequest request);
     ResponseEntity getFile() ;
}

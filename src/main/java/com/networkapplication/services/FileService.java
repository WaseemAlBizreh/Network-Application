package com.networkapplication.services;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.List;

public interface FileService {
     FileDTOResponse fileUpload(FileDTORequest request);
     FileDTOResponse loadFile(Long id);
     List loadAllGroupFiles(Long groupId);
}

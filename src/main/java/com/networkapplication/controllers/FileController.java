package com.networkapplication.controllers;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.exceptions.GlobalExceptionHandler;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {

    private final GlobalExceptionHandler exceptionHandler;
    private final FileService services;

    @PostMapping("/upload")
    public ResponseEntity<MainDTO> fileUpload(@ModelAttribute FileDTORequest file)  {
        try {
            return ResponseEntity.ok(services.fileUpload(file));
        }catch (ResponseException ex){
            return exceptionHandler.handleException(ex);
        }

    }


    @GetMapping("/load/{file_id}")
    public ResponseEntity<MainDTO> loadFile(@PathVariable Long file_id) {
        try {
            return ResponseEntity.ok(services.loadFile(file_id));
        }catch (ResponseException ex){
            return exceptionHandler.handleException(ex);
        }
    }

    @DeleteMapping("/deleteAllFiles")
    public ResponseEntity<MainDTO> deleteAllFiles(@PathVariable Long groupId)  {
        try {
            return ResponseEntity.ok(services.deleteAllInGroup(groupId));
        }catch (ResponseException ex){
            return exceptionHandler.handleException(ex);
        }
    }
}

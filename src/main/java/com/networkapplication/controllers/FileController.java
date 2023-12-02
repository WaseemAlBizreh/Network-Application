package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {

    private FileService services;

    @PostMapping("/upload")
    public ResponseEntity<MessageDTO> fileUpload(@RequestBody FileDTORequest file) {
        return ResponseEntity.ok(services.fileUpload(file));
    }


    @GetMapping("/load/{file_id}")
    public ResponseEntity<FileDTOResponse> loadFile(@PathVariable Long file_id) {
        try {
            return ResponseEntity.ok(services.loadFile(file_id));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}

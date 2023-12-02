package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {

    private FileService services;

    @PostMapping("/upload")
    public ResponseEntity<MessageDTO> fileUpload(@RequestBody FileDTORequest file) {
        return ResponseEntity.ok(services.fileUpload(file));
    }


    @GetMapping("/getFile")
    public ResponseEntity getFile(@RequestBody UserDTORequest user) {
        return services.getFile();
    }


}

package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService services;

    @PostMapping("/upload")
    public ResponseEntity<FileDTOResponse> fileUpload(@ModelAttribute FileDTORequest file) {
        return ResponseEntity.ok(services.fileUpload(file));
    }


    @GetMapping("/load/{file_id}")
    public ResponseEntity<FileDTOResponse> loadFile(@PathVariable Long file_id) {
        return ResponseEntity.ok(services.loadFile(file_id));
    }
}

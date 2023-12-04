package com.networkapplication.controllers;

import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.exceptions.GlobalExceptionHandler;
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
    public ResponseEntity<MainDTO> fileUpload(@ModelAttribute FileDTORequest file) {
        return ResponseEntity.ok(services.fileUpload(file));
    }


    @GetMapping("/load/{file_id}")
    public ResponseEntity<MainDTO> loadFile(@PathVariable Long file_id) {
        return ResponseEntity.ok(services.loadFile(file_id));
    }
}

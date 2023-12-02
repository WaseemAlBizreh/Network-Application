package com.networkapplication.controllers;

import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Request.UserDTORequest;
import com.networkapplication.dtos.Response.MessageDTO;
import com.networkapplication.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {

    private FileService services;

    @PostMapping("/addFile")
    public MessageDTO addFile(@RequestBody FileDTORequest file){
        return services.addFile(file);
    }

    @GetMapping("/getFile")
    public ResponseEntity getFile(@RequestBody UserDTORequest user) {
        return services.getFile();
    }


}

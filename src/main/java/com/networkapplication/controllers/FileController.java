package com.networkapplication.controllers;

import com.networkapplication.FileStorage.FileStorageManager;
import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.Request.CheckInDTO;
import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.dtos.Request.FileName;
import com.networkapplication.dtos.Response.FileDTOResponse;
import com.networkapplication.exceptions.GlobalExceptionHandler;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.services.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {

    private final GlobalExceptionHandler exceptionHandler;
    private final FileService services;
    @Autowired
    private FileStorageManager fileStorageManager;

    @DeleteMapping("/deleteAllFiles/{groupId}")
    public ResponseEntity<MainDTO> deleteAllFiles(@PathVariable Long groupId) {
        try {
            return ResponseEntity.ok(services.deleteAllFilesInGroup(groupId));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    @GetMapping("/getAllFiles/{group_id}")
    public ResponseEntity<MainDTO> getAllFiles(@PathVariable Long group_id) {
        try {
            return ResponseEntity.ok(services.loadAllGroupFiles(group_id));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }


    @PostMapping("/addFile/{group_id}")
    public ResponseEntity<MainDTO> createFile(@RequestParam MultipartFile file, @PathVariable Long group_id) throws ResponseException, IOException {
        try {
            return ResponseEntity.ok(services.createFile(file, group_id));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }

    @PostMapping("/checkIn")
    public ResponseEntity<MainDTO> checkIn(@RequestBody CheckInDTO check) throws ResponseException {
        try{
            return ResponseEntity.ok(services.checkIn(check));
        }catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    @PostMapping("/checkOut")
    public ResponseEntity<MainDTO> checkOut(@RequestBody CheckInDTO checkOut) throws ResponseException {
        try {
            return ResponseEntity.ok(services.checkOut(checkOut));
        }catch (ResponseException ex){
            return exceptionHandler.handleException(ex);
        }
    }

    @PostMapping("/updateFile/{group_id}")
    public ResponseEntity<MainDTO> updateFile(@RequestParam MultipartFile file, @PathVariable Long group_id) throws ResponseException, IOException {
        try {
            return ResponseEntity.ok(services.updateFile(file, group_id));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }

    @DeleteMapping("/deleteFile/{group_id}")
    public ResponseEntity<MainDTO> deleteFile(@PathVariable Long group_id,@RequestBody CheckInDTO files_id) throws ResponseException{
        try {
            return ResponseEntity.ok(services.deleteFile(group_id,files_id));
        }catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }


    @PostMapping("/getFile/{group_id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long group_id ,@RequestBody FileName name) throws ResponseException {
        try {
            return services.getFile(group_id,name);
        }catch (ResponseException ex){
            throw new ResponseException(ex.getStatusCode(),ex.getMessage());
        }


    }
}




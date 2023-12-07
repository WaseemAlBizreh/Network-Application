package com.networkapplication.controllers;

import com.networkapplication.FileStorage.FileStorageManager;
import com.networkapplication.dtos.MainDTO;
import com.networkapplication.dtos.Request.CheckInDTO;
import com.networkapplication.dtos.Request.FileDTORequest;
import com.networkapplication.exceptions.GlobalExceptionHandler;
import com.networkapplication.exceptions.ResponseException;
import com.networkapplication.services.FileService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {

    private final GlobalExceptionHandler exceptionHandler;
    private final FileService services;
    @Autowired
    private FileStorageManager fileStorageManager;

//    @PostMapping("/upload")
//    public ResponseEntity<MainDTO> fileUpload(@ModelAttribute FileDTORequest file)  {
//        try {
//            return ResponseEntity.ok(services.fileUpload(file));
//        }catch (ResponseException ex){
//            return exceptionHandler.handleException(ex);
//        }
//
//    }
//
//
//    @GetMapping("/load/{file_id}")
//    public ResponseEntity<MainDTO> loadFile(@PathVariable Long file_id) {
//        try {
//            return ResponseEntity.ok(services.loadFile(file_id));
//        }catch (ResponseException ex){
//            return exceptionHandler.handleException(ex);
//        }
//    }

    @DeleteMapping("/deleteAllFiles")
    public ResponseEntity<MainDTO> deleteAllFiles(@PathVariable Long groupId) {
        try {
            return ResponseEntity.ok(services.deleteAllInGroup(groupId));
        } catch (ResponseException ex) {
            return exceptionHandler.handleException(ex);
        }
    }

    @PostMapping("/getFile")
    public ResponseEntity<MainDTO> getFile(@RequestBody FileDTORequest fileDTORequest) throws ResponseException {
        return ResponseEntity.ok(services.getFile(fileDTORequest));
    }

    @RequestMapping (path = "/addFile", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MainDTO> addFile(@RequestParam MultipartFile file) throws IOException,ResponseException{
        fileStorageManager.save(file);
        return ResponseEntity.ok(services.saveFile(file));
    }

    @PostMapping("/checkIn")
    public ResponseEntity<MainDTO> checkIn(@RequestBody CheckInDTO check) throws ResponseException {
        return ResponseEntity.ok(services.checkIn(check));
    }

    @PostMapping("/checkOut")
    public ResponseEntity<MainDTO> checkOut(@RequestBody CheckInDTO checkOut) throws ResponseException {
        return ResponseEntity.ok(services.checkOut(checkOut));
    }
}

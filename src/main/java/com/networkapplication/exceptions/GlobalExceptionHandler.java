package com.networkapplication.exceptions;

import com.networkapplication.dtos.MainDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<MainDTO> handleException(ResponseException ex) {
        ErrorDTO error = ErrorDTO.builder().error(ex.getMessage()).build();
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

}

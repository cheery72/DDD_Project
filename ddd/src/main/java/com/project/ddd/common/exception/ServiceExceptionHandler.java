package com.project.ddd.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ServiceExceptionHandler {

    @ExceptionHandler(NoSuchBoardException.class)
    protected ResponseEntity<?> handleNoSuchBoardException(NoSuchBoardException e){
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("Board Not Found")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(NoSuchCommentException.class)
    protected ResponseEntity<?> handleNoSuchCommentException(NoSuchCommentException e){
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("Comment Not Found")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}

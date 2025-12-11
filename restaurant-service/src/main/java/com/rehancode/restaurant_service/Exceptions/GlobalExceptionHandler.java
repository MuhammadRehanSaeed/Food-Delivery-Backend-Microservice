package com.rehancode.restaurant_service.Exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
      @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> BadRequest(BadRequestException ex){
        ApiResponse<Object> response=new ApiResponse<Object>(400, false, ex.getMessage(), null);
        return ResponseEntity.status(400).body(response);
    } 
      @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> ResourceNotFound(ResourceNotFoundException ex){
        ApiResponse<Object> response=new ApiResponse<Object>(404, false, ex.getMessage(), null);
        return ResponseEntity.status(404).body(response);
    } 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex){
       ApiResponse<Object> response=new ApiResponse<Object>(500, false, ex.getMessage(), null);
         return ResponseEntity.status(500).body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    StringBuilder errors = new StringBuilder();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
        errors.append(error.getField())
              .append(": ")
              .append(error.getDefaultMessage())
              .append("; ");
    }
    ApiResponse<Object> response = new ApiResponse<>( 400, false,errors.toString(), null);
     return ResponseEntity.badRequest().body(response);
}



  
}

package com.washtrack.washtrack_api.util.exceptions;

import com.washtrack.washtrack_api.util.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ServiceResult<Object>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    
    // Collect all validation error messages
    String mensajes = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));
    
    log.warn("[Validacion fallida | Detalles: {}]", mensajes);
    
    ServiceResult<Object> resultado = new ServiceResult<>(
        false,
        mensajes,
        0,
        null
    );
    
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
  }
  
}

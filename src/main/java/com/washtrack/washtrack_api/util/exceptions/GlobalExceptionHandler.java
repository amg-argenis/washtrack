package com.washtrack.washtrack_api.util.exceptions;

import com.washtrack.washtrack_api.util.response.ServiceResult;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
  
  // Metodo para validaciones con RequestParam como en "/entregas/buscar/entregaRequest"
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ServiceResult<Object>> handleConstraintViolation(
      ConstraintViolationException ex) {
    
    String mensajes = ex.getConstraintViolations()
        .stream()
        .map(violation -> violation.getMessage())
        .collect(Collectors.joining(", "));
    
    log.warn("[Validacion de parametros fallida | Detalles: {}]", mensajes);
    
    ServiceResult<Object> resultado = new ServiceResult<>(
        false,
        mensajes,
        0,
        null
    );
    
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
  }
  
  // Manejo de formatos de fecha en request
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ServiceResult<Object>> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex) {
    
    log.warn("[Formato de datos invalido | Detalles: {}]", ex.getMessage());
    
    ServiceResult<Object> resultado = new ServiceResult<>(
        false,
        "Formato de datos invalido, favor de verificar el request",
        0,
        null
    );
    
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
  }
  
}

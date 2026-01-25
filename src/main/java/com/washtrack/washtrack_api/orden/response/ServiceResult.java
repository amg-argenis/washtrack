package com.washtrack.washtrack_api.orden.response;

import lombok.Data;

@Data
public class ServiceResult<T> {
  
  private boolean success;
  private String message;
  private T data;
  
  public ServiceResult(boolean success, String message, T data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }
}

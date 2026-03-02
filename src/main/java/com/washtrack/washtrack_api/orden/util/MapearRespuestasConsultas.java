package com.washtrack.washtrack_api.orden.util;

import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MapearRespuestasConsultas {
  
  public <T> ServiceResult<T> mapearserviceResultRespuestaOk(String mensaje,
      int registros, T datos) {
    // Setear la respuesta por default
    ServiceResult<T> serviceResult = new ServiceResult<>(
        false,
        ConstantesOrdenes.RESPUESTA_GENERICA,
        ConstantesNumericas.CERO,
        null
    );
    
    serviceResult.setSuccess(true);
    serviceResult.setMessage(mensaje);
    serviceResult.setRegistros(registros);
    serviceResult.setData(datos);
    
    return serviceResult;
  }
  
  public <T> ServiceResult<T> mapearserviceResultError(String mensaje, T datos) {
    // Setear la respuesta por default
    ServiceResult<T> serviceResult = new ServiceResult<>(
        false,
        ConstantesOrdenes.RESPUESTA_GENERICA,
        ConstantesNumericas.CERO,
        null
    );
    
    serviceResult.setSuccess(false);
    serviceResult.setMessage(mensaje);
    serviceResult.setRegistros(ConstantesNumericas.CERO);
    serviceResult.setData(datos);
    
    return serviceResult;
  }
  
}

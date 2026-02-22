package com.washtrack.washtrack_api.orden.util;

import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MapearRespuestasConsultas {
  
  public <T> ServiceResult<T> mapearserviceResultBuscarOrdenDetalle(boolean estatus, String mensaje,
      int registros, T datos) {
    // Setear la respuesta por default
    ServiceResult<T> serviceResult = new ServiceResult<>(
        false,
        ConstantesOrdenes.SIN_REGISTROS,
        ConstantesNumericas.CERO,
        null
    );
    serviceResult.setSuccess(estatus);
    serviceResult.setMessage(mensaje);
    serviceResult.setRegistros(registros);
    serviceResult.setData(datos);
    
    return serviceResult;
  }
  
}

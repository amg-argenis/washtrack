package com.washtrack.washtrack_api.orden.service.ordencondetalle;

import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.service.IOrdentesConDetalleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrdentesConDetalleServiceImpl implements IOrdentesConDetalleService {
  
  @Override
  public ServiceResult<Object> obtenerOrdenServicioMasDetallesDto(BuscarOrdenRequest ordenRequest) {
    return null;
  }
  
}

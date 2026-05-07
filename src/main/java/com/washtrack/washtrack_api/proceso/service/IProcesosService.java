package com.washtrack.washtrack_api.proceso.service;

import com.washtrack.washtrack_api.proceso.dto.ProcesosRequest;
import com.washtrack.washtrack_api.util.response.ServiceResult;

public interface IProcesosService {
  ServiceResult<Object> insertarProcesoService(ProcesosRequest procesosRequest);
  ServiceResult<Object> buscarProcesoService(String codigoProceso, String tenantid);
}

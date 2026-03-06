package com.washtrack.washtrack_api.orden.respository;

import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.dto.orden.OrdenServicioMasDetallesDto;

public interface IOrdenConDetalleRepository {
  OrdenServicioMasDetallesDto obtenerOrdenServicioMasDetallesRepository(BuscarOrdenRequest ordenRequest);
}

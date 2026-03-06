package com.washtrack.washtrack_api.orden.respository;

import com.washtrack.washtrack_api.orden.dto.orden.BuscarOrdenRequest;
import com.washtrack.washtrack_api.orden.entity.OrdenServicioMasDetallesEntity;

public interface IOrdenConDetalleRepository {
  OrdenServicioMasDetallesEntity obtenerOrdenServicioMasDetallesRepository(BuscarOrdenRequest ordenRequest);
}

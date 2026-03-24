package com.washtrack.washtrack_api.orden.rowmapper;

import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.orden.entity.OrdenServicioMasDetallesEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrdenConDetalleMapper implements RowMapper<OrdenServicioMasDetallesEntity> {
  
  @Override
  public OrdenServicioMasDetallesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    
    DetalleOrdenEntity detalle = DetalleOrdenEntity.builder()
        .idDetalleOrden(rs.getString("idDetalleOrden"))
        .ordenId(rs.getString("ordenId"))
        .procesoId(rs.getString("procesoId"))
        .tipoPrenda(rs.getString("tipoPrenda"))
        .cantidad(rs.getInt("cantidad"))
        .colorReferencia(rs.getString("colorReferencia"))
        // alias del SP
        .tenantId(rs.getString("tenantIdDetalle"))
        .build();
    
    return OrdenServicioMasDetallesEntity.builder()
        .idOrden(rs.getString("idOrden"))
        .clienteId(rs.getString("clienteId"))
        .folio(rs.getString("folio"))
        .fechaIngreso(rs.getString("fechaIngreso"))
        .estado(rs.getString("estado"))
        .totalPrendas(rs.getInt("totalPrendas"))
        .observaciones(rs.getString("observaciones"))
        .createdAt(rs.getString("createdAt"))
        .tenantId(rs.getString("tenantId"))
        .fechaEntrega(rs.getString("fechaEntrega"))
        .ordenesDetalleDto(List.of(detalle))
        .build();
  }
}

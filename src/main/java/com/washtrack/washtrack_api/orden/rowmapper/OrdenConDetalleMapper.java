package com.washtrack.washtrack_api.orden.rowmapper;

import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.orden.entity.OrdenServicioMasDetallesEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdenConDetalleMapper implements RowMapper<OrdenServicioMasDetallesEntity> {
  
  @Override
  public OrdenServicioMasDetallesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    
    // Handle null details from LEFT JOIN
    DetalleOrdenEntity detalle = null;
    if ( rs.getString("idDetalleOrden") != null ) {
      detalle = DetalleOrdenEntity.builder()
          .idDetalleOrden(rs.getString("idDetalleOrden"))
          .ordenId(rs.getString("ordenIdDetalle")) // alias del SP
          .procesoId(rs.getString("procesoId"))
          .tipoPrenda(rs.getString("tipoPrenda"))
          .cantidad(rs.getInt("cantidad"))
          .colorReferencia(rs.getString("colorReferencia"))
          .tenantId(rs.getString("tenantIdDetalle"))
          .build();
    }
    
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
        .ordenesDetalleDto(detalle != null ? List.of(detalle) : new ArrayList<>())
        .build();
  }
  
}
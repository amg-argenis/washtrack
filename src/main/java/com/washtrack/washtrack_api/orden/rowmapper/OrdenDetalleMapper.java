package com.washtrack.washtrack_api.orden.rowmapper;

import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdenDetalleMapper implements RowMapper<DetalleOrdenEntity> {
  
  @Override
  public DetalleOrdenEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    
    return DetalleOrdenEntity.builder()
        .idDetalleOrden(rs.getString("idDetalleOrden"))
        .ordenId(rs.getString("ordenId"))
        .procesoId(rs.getString("procesoId"))
        .tipoPrenda(rs.getString("tipoPrenda"))
        .cantidad(rs.getInt("cantidad"))
        .colorReferencia(rs.getString("colorReferencia"))
        .tenantId(rs.getString("tenantId"))
        .build();
  }
}

package com.washtrack.washtrack_api.orden.rowmapper;

import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdenesMapper implements RowMapper<OrdenesEntity> {
  
  @Override
  public OrdenesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    
    return OrdenesEntity.builder()
        .idOrden(rs.getString("idOrden"))
        .clienteId(rs.getString("clienteId"))
        .folio(rs.getString("folio"))
        .fechaIngreso(String.valueOf(rs.getDate("fechaIngreso")))
        .estado(rs.getString("estado"))
        .totalPrendas(rs.getInt("totalPrendas"))
        .observaciones(rs.getString("observaciones"))
        .tenantId(rs.getString("tenantId"))
        .fechaEntrega(String.valueOf(rs.getDate("fechaEntrega")))
        .build();
  }
  
}

package com.washtrack.washtrack_api.orden.rowmapper;

import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdenesMapper implements RowMapper<OrdenesEntity> {
  
  @Override
  public OrdenesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    OrdenesEntity ordenesEntity = new OrdenesEntity();
    ordenesEntity.setIdOrden(rs.getString("idOrden"));
    ordenesEntity.setClienteId(rs.getString("clienteId"));
    ordenesEntity.setFolio(rs.getString("folio"));
    ordenesEntity.setFechaIngreso(String.valueOf(rs.getDate("fechaIngreso")));
    ordenesEntity.setEstado(rs.getString("estado"));
    ordenesEntity.setTotalPrendas(rs.getInt("totalPrendas"));
    ordenesEntity.setObservaciones(rs.getString("observaciones"));
    ordenesEntity.setTenantId(rs.getString("tenantId"));
    
    return ordenesEntity;
  }
  
}

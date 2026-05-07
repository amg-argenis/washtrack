package com.washtrack.washtrack_api.proceso.rowmapper;

import com.washtrack.washtrack_api.proceso.entity.ProcesosEntity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProcesosRowmapper implements RowMapper<ProcesosEntity> {
  
  @Override
  public ProcesosEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    
    return ProcesosEntity.builder()
        .idproceso(rs.getString("idproceso"))
        .tenantid(rs.getString("tenantid"))
        .nombre(rs.getString("nombre"))
        .preciounitario(rs.getBigDecimal("preciounitario"))
        .descripcion(rs.getString("descripcion"))
        .activo(rs.getBoolean("activo"))
        .codigo(rs.getString("codigo"))
        .build();
  }
}

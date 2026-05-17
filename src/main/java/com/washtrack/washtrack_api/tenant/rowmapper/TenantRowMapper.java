package com.washtrack.washtrack_api.tenant.rowmapper;

import com.washtrack.washtrack_api.tenant.entity.TenantEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TenantRowMapper implements RowMapper<TenantEntity> {
  
  @Override
  public TenantEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return TenantEntity.builder()
        .idtenant(rs.getString("idTenant"))
        .nombre(rs.getString("nombre"))
        .activo(rs.getBoolean("activo"))
        .createdat(rs.getString("createdAt"))
        .build();
  }
}
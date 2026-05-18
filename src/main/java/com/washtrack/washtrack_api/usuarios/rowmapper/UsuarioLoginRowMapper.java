package com.washtrack.washtrack_api.usuarios.rowmapper;

import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioLoginRowMapper implements RowMapper<UsuarioEntity> {
  
  @Override
  public UsuarioEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return UsuarioEntity.builder()
        .idUsuario(rs.getString("idUsuario"))
        .tenantId(rs.getString("tenantId"))
        .nombre(rs.getString("nombre"))
        .email(rs.getString("email"))
        .rol(rs.getString("rol"))
        .activo(rs.getBoolean("activo"))
        .createdAt(String.valueOf(rs.getTimestamp("createdAt")))
        .nombreTenant(rs.getString("nombreTenant"))
        .build();
  }
}

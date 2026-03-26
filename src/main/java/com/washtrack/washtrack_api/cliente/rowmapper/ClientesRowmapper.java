package com.washtrack.washtrack_api.cliente.rowmapper;

import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientesRowmapper implements RowMapper<ClientesEntity> {
  
  @Override
  public ClientesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return ClientesEntity.builder()
        .idCliente(rs.getString("idCliente"))
        .tenantId(rs.getString("tenantId"))
        .nombre(rs.getString("nombre"))
        .contacto(rs.getString("contacto"))
        .telefono(rs.getString("telefono"))
        .email(rs.getString("email"))
        .creditoHabilitado(rs.getBoolean("creditoHabilitado"))
        .limiteCredito(rs.getDouble("limiteCredito"))
        .activo(rs.getString("activo"))
        .build();
  }
}

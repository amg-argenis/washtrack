package com.washtrack.washtrack_api.entregas.rowmapper;

import com.washtrack.washtrack_api.entregas.entity.EntregasEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntregaRowMapper implements RowMapper<EntregasEntity> {
  @Override
  public EntregasEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return EntregasEntity.builder()
        .idEntrega(rs.getString("idEntrega"))
        .tenantId(rs.getString("tenantId"))
        .ordenId(rs.getString("ordenId"))
        .fechaEntrega(rs.getString("fechaEntrega"))
        .totalEntregado(rs.getInt("totalEntregado"))
        .conformidadCliente(rs.getBoolean("conformidadCliente"))
        .observaciones(rs.getString("observaciones"))
        .estado(rs.getString("estado"))
        .fechaCreacion(rs.getString("fechaCreacion"))
        .build();
  }
}

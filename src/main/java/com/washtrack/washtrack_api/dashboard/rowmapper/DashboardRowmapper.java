package com.washtrack.washtrack_api.dashboard.rowmapper;

import com.washtrack.washtrack_api.dashboard.entity.DashboardEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardRowmapper implements RowMapper<DashboardEntity> {
  @Override
  public DashboardEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return DashboardEntity.builder()
        .ordenesActivas(rs.getString("ordenesActivas"))
        .clientesActivos(rs.getString("clientesActivos"))
        .entregasMes(rs.getString("entregasMes"))
        .prendasEnProceso(rs.getString("prendasEnProceso"))
        .ordenesListas(rs.getString("ordenesListas"))
        .ordenesEntregadasMes(rs.getString("ordenesEntregadasMes"))
        .build();
  }
}

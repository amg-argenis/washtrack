package com.washtrack.washtrack_api.dashboard.rowmapper;

import com.washtrack.washtrack_api.dashboard.entity.DashboardEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardRowmapper implements RowMapper<DashboardEntity> {
  @Override
  public DashboardEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return DashboardEntity.builder()
        .ordenesActivas(rs.getInt("ordenesActivas"))
        .clientesActivos(rs.getInt("clientesActivos"))
        .entregasMes(rs.getInt("entregasMes"))
        .prendasEnProceso(rs.getInt("prendasEnProceso"))
        .ordenesListas(rs.getInt("ordenesListas"))
        .ordenesEntregadasMes(rs.getInt("ordenesEntregadasMes"))
        .build();
  }
}

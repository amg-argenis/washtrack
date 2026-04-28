package com.washtrack.washtrack_api.dashboard.repository.inicializador;

import com.washtrack.washtrack_api.dashboard.rowmapper.DashboardRowmapper;
import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InicializadorDashboard {
  
  private SimpleJdbcCall obtenerDashboardJdbcCall;
  
  private final JdbcTemplate jdbcTemplate;
  
  public InicializadorDashboard(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  
  @PostConstruct
  public void init() {
    this.obtenerDashboardJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_DASHBOARD)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("dashboardrecuperado", new DashboardRowmapper());
  }
  
  // EJECUCIONES ******************************************************************************************************
  public Map<String, Object> obtenerDashboardMethod(String tenantId) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", tenantId);
    return this.obtenerDashboardJdbcCall.execute(params);
  }
}

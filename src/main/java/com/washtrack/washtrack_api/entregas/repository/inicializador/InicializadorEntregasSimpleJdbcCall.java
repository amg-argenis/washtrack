package com.washtrack.washtrack_api.entregas.repository.inicializador;

import com.washtrack.washtrack_api.entregas.entity.EntregasEntity;
import com.washtrack.washtrack_api.entregas.rowmapper.EntregaRowMapper;
import com.washtrack.washtrack_api.entregas.util.MapearObjetosEntregas;
import com.washtrack.washtrack_api.usuarios.rowmapper.UsuarioRowMapper;
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
public class InicializadorEntregasSimpleJdbcCall {
  
  private SimpleJdbcCall insertarEntregaSimpleJdbcCall;
  private SimpleJdbcCall buscarEntregaSimpleJdbcCall;
  
  private final JdbcTemplate jdbcTemplate;
  private final MapearObjetosEntregas mapearObjetosEntregas;
  
  public InicializadorEntregasSimpleJdbcCall(JdbcTemplate jdbcTemplate, MapearObjetosEntregas mapearObjetosEntregas) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapearObjetosEntregas = mapearObjetosEntregas;
  }
  
  @PostConstruct
  public void init() {
    this.insertarEntregaSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_INSERTAR_ENTREGA)
        .declareParameters(
            // IN
            new SqlParameter("pa_identrega", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_ordenid", Types.VARCHAR),
            new SqlParameter("pa_fechaentrega", Types.VARCHAR),
            new SqlParameter("pa_totalentregado", Types.INTEGER),
            new SqlParameter("pa_conformidad", Types.TINYINT),
            new SqlParameter("pa_observaciones", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("entregarecuperada", new EntregaRowMapper());
    
    this.buscarEntregaSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_BUSCAR_ENTREGA)
        .declareParameters(
            // IN
            new SqlParameter("pa_identrega", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("entregarecuperada", new EntregaRowMapper());
  }
  
  // EJECUCIONES ******************************************************************************************************
  
  public Map<String, Object> insertarEntregaJdbcMethod(EntregasEntity entregasEntity) {
    Map<String, Object> params = this.mapearObjetosEntregas.insertarEntregaParams(entregasEntity);
    
    return this.insertarEntregaSimpleJdbcCall.execute(params);
  }
  
  public Map<String, Object> buscarEntregaJdbcMethod(String idEntrega, String tenantId) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_identrega", idEntrega);
    params.put("pa_tenantid", tenantId);
    
    return this.buscarEntregaSimpleJdbcCall.execute(params);
  }
  
}

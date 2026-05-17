package com.washtrack.washtrack_api.tenant.repository.inicializador;

import com.washtrack.washtrack_api.tenant.entity.TenantEntity;
import com.washtrack.washtrack_api.tenant.rowmapper.TenantRowMapper;
import com.washtrack.washtrack_api.tenant.util.MapearObjetosTenant;
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
public class InicializadorTenant {
  
  private SimpleJdbcCall insertarTenant;
  private SimpleJdbcCall listarTenants;
  private SimpleJdbcCall buscarTenant;
  private SimpleJdbcCall actualizarTenant;
  private SimpleJdbcCall eliminarTenant;
  
  private final JdbcTemplate jdbcTemplate;
  private final MapearObjetosTenant mapearObjetosTenant;
  
  public InicializadorTenant(JdbcTemplate jdbcTemplate, MapearObjetosTenant mapearObjetosTenant) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapearObjetosTenant = mapearObjetosTenant;
  }
  
  @PostConstruct
  public void init() {
    this.insertarTenant = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_INSERTAR_TENANT)
        .declareParameters(
            // IN
            new SqlParameter("pa_idtenant", Types.VARCHAR),
            new SqlParameter("pa_nombre", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("tenantinsertado", new TenantRowMapper());
    
    this.listarTenants = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_LISTAR_TENANTS)
        .declareParameters(
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("listatenants", new TenantRowMapper());
    
    this.buscarTenant = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_BUSCAR_TENANT)
        .declareParameters(
            // IN
            new SqlParameter("pa_idtenant", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("tenantrecuperado", new TenantRowMapper());
    
    this.actualizarTenant = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_ACTUALIZAR_TENANT)
        .declareParameters(
            // IN
            new SqlParameter("pa_idtenant", Types.VARCHAR),
            new SqlParameter("pa_nombre", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("tenantactualizado", new TenantRowMapper());
    
    this.eliminarTenant = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_ELIMINAR_TENANT)
        .declareParameters(
            // IN
            new SqlParameter("pa_idtenant", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        );
  }
  
  // EJECUCIONES EN BD
  public Map<String, Object> insertarTenantExe(TenantEntity tenant) {
    Map<String, Object> paramMap = this.mapearObjetosTenant.parametrizarTenantInsert(tenant);
    log.info("[Parametros del nuevo tenant a insertar | Detalle: {}]", paramMap);
    return this.insertarTenant.execute(paramMap);
  }
  
  public Map<String, Object> listarTenantsExe() {
    Map<String, Object> paramMap = new HashMap<>();
    log.info("[Parametros para listar tenants | Detalle: {}]", paramMap);
    return this.listarTenants.execute(paramMap);
  }
  
  public Map<String, Object> buscarTenantExe(String idtenant) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pa_idtenant", idtenant);
    log.info("[Parametros del tenant a buscar | Detalle: {}]", paramMap);
    return this.buscarTenant.execute(paramMap);
  }
  
  public Map<String, Object> actualizarTenantExe(TenantEntity tenant) {
    Map<String, Object> paramMap = this.mapearObjetosTenant.parametrizarTenantUpdate(tenant);
    log.info("[Parametros del tenant a actualizar | Detalle: {}]", paramMap);
    return this.actualizarTenant.execute(paramMap);
  }
  
  public Map<String, Object> eliminarTenantExe(String idtenant) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("pa_idtenant", idtenant);
    log.info("[Parametros del tenant a eliminar | Detalle: {}]", paramMap);
    return this.eliminarTenant.execute(paramMap);
  }
}
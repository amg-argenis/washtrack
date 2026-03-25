package com.washtrack.washtrack_api.orden.respository.inicializador;

import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenBaseDatos;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.rowmapper.OrdenesMapper;
import com.washtrack.washtrack_api.orden.util.MapearObjetos;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InicializadorSimpleJdbcCall {
  
  private SimpleJdbcCall listarOrdenesCall;
  private SimpleJdbcCall listarFechaIngresoOrdenesCall;
  private SimpleJdbcCall buscarOrdenCall;
  private SimpleJdbcCall insertarOrdenCall;
  private SimpleJdbcCall actualizarOrdenCall;
  private SimpleJdbcCall eliminarOrdenCall;
  
  private final JdbcTemplate jdbcTemplate;
  private final MapearObjetos mapearObjetos;
  
  public InicializadorSimpleJdbcCall(JdbcTemplate jdbcTemplate, MapearObjetos mapearObjetos) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapearObjetos = mapearObjetos;
  }
  
  @PostConstruct
  public void init() {
    this.listarOrdenesCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesOrdenBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesOrdenBaseDatos.SP_LISTAR_ORDENES)
        .declareParameters(
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlOutParameter(ConstantesOrdenBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesOrdenBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("listaOrdenes", new OrdenesMapper());
    
    this.listarFechaIngresoOrdenesCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesOrdenBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesOrdenBaseDatos.SP_LISTARPOR_FECHAINGRESO)
        .declareParameters(
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_fechaingreso", Types.VARCHAR),
            new SqlOutParameter(ConstantesOrdenBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesOrdenBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("listaOrdenes", new OrdenesMapper());
    
    /**
     * Buscar una orden de servicio
     */
    this.buscarOrdenCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesOrdenBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesOrdenBaseDatos.SP_BUSCAR_ORDENSERVICIO)
        .declareParameters(
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_idorden", Types.VARCHAR),
            new SqlParameter("pa_ordenfolio", Types.VARCHAR),
            new SqlOutParameter(ConstantesOrdenBaseDatos.PAMENSAJEBD, Types.VARCHAR),
            new SqlOutParameter(ConstantesOrdenBaseDatos.CODIGOBD, Types.INTEGER)
        )
        .returningResultSet("ordenrecuperada", new OrdenesMapper());
    
    this.insertarOrdenCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesOrdenBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesOrdenBaseDatos.SP_INSERTAR_ORDENSERVICIO)
        .declareParameters(
            // IN
            new SqlParameter("pa_idorden", Types.VARCHAR),
            new SqlParameter("pa_clienteid", Types.VARCHAR),
            new SqlParameter("pa_fechaingreso", Types.VARCHAR),
            new SqlParameter("pa_estado", Types.VARCHAR),
            new SqlParameter("pa_totalprendas", Types.INTEGER),
            new SqlParameter("pa_observaciones", Types.VARCHAR),
            new SqlParameter("pa_fechaentrega", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            // OUT control
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR),
            // OUT campos insertados
            new SqlOutParameter("po_idorden", Types.VARCHAR),
            new SqlOutParameter("po_clienteid", Types.VARCHAR),
            new SqlOutParameter("po_folio", Types.VARCHAR),
            new SqlOutParameter("po_fechaingreso", Types.VARCHAR),
            new SqlOutParameter("po_estado", Types.VARCHAR),
            new SqlOutParameter("po_totalprendas", Types.INTEGER),
            new SqlOutParameter("po_observaciones", Types.VARCHAR),
            new SqlOutParameter("po_createdat", Types.VARCHAR),
            new SqlOutParameter("po_tenantid", Types.VARCHAR),
            new SqlOutParameter("po_fechaentrega", Types.VARCHAR)
        );
    
    this.actualizarOrdenCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesOrdenBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesOrdenBaseDatos.SP_ACTUALIZAR_ORDENSERVICIO)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_idorden", Types.VARCHAR),
            new SqlParameter("pa_clienteid", Types.VARCHAR),
            new SqlParameter("pa_folio", Types.VARCHAR),
            new SqlParameter("pa_fechaingreso", Types.VARCHAR),
            new SqlParameter("pa_estado", Types.VARCHAR),
            new SqlParameter("pa_totalprendas", Types.VARCHAR),
            new SqlParameter("pa_observaciones", Types.VARCHAR),
            new SqlParameter("pa_fechaentrega", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        );
    
    this.eliminarOrdenCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesOrdenBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesOrdenBaseDatos.SP_ELIMINAR_ORDENSERVICIO)
        .declareParameters(
            // IN
            new SqlParameter("pa_idorden", Types.VARCHAR),
            new SqlParameter("pa_folio", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        );
    
  }
  
  // EJECUCIONES EN BD *************************************************************************************************
  
  /**
   * Listar ordenes de servicio | Inithializer
   *
   * @return
   */
  public Map<String, Object> listarOrdenesCallJdbc(String tenantId) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", tenantId);
    return this.listarOrdenesCall.execute(params);
  }
  
  /**
   * Listar ordenes de servicio | Inithializer
   *
   * @return
   */
  public Map<String, Object> listarOrdenesFechaIngresoCallJdbc(String tenantId, LocalDate fechaIngreso) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", tenantId);
    params.put("pa_fechaingreso", fechaIngreso);
    return this.listarFechaIngresoOrdenesCall.execute(params);
  }
  
  /**
   * Buscar una orden de servicio | Inithializer
   *
   * @param orden
   * @return
   */
  public Map<String, Object> buscarOrdenCallJdbc(OrdenesEntity orden) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", "a051a168-fa2a-11f0-aab7-e66133dbb0de"); // hardcodeado por ahora
    params.put("pa_idorden", orden.getIdOrden());
    params.put("pa_ordenfolio", orden.getFolio());
    
    return this.buscarOrdenCall.execute(params);
  }
  
  /**
   * Guardar una nueva orden de servicio | Inithializer
   *
   * @param orden
   * @return
   */
  public Map<String, Object> insertarOrden(OrdenesEntity orden) {
    Map<String, Object> paramMap = this.mapearObjetos.parametrizarOrdenes(orden);
    log.info("[Parametros de la nueva orden a insertar | Detalle: {}]", paramMap);
    return this.insertarOrdenCall.execute(paramMap);
  }
  
  /**
   * Actualizar una nueva orden de servicio | Inithializer
   *
   * @param orden
   * @return
   */
  public Map<String, Object> actualizarOrden(OrdenesEntity orden) {
    Map<String, Object> paramMap = this.mapearObjetos.parametrizarActualizarOrdenes(orden);
    log.info("[Parametros para actualizar una orden de servicio]: {}", paramMap);
    return this.actualizarOrdenCall.execute(paramMap);
  }
  
  /**
   * Eliminar una nueva orden de servicio | Inithializer
   *
   * @param orden
   * @return
   */
  public Map<String, Object> eliminarOrden(OrdenesEntity orden) {
    Map<String, Object> paramMap = this.mapearObjetos.parametrizarEliminarOrdenes(orden);
    log.info("[Parametros para eliminar una orden de servicio]: {}", paramMap);
    return this.eliminarOrdenCall.execute(paramMap);
  }
  
}

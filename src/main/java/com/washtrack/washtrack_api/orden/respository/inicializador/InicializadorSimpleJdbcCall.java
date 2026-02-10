package com.washtrack.washtrack_api.orden.respository.inicializador;

import com.washtrack.washtrack_api.orden.constants.ConstantesBaseDatos;
import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.rowmapper.OrdenesMapper;
import com.washtrack.washtrack_api.orden.util.MapearObjetos;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InicializadorSimpleJdbcCall {
  
  private SimpleJdbcCall listarOrdenesCall;
  private SimpleJdbcCall listarFechaIngresoOrdenesCall;
  private SimpleJdbcCall buscarOrdenCall;
  private SimpleJdbcCall insertarOrdenCall;
  
  private final JdbcTemplate jdbcTemplate;
  private final MapearObjetos mapearObjetos;
  
  public InicializadorSimpleJdbcCall(JdbcTemplate jdbcTemplate, MapearObjetos mapearObjetos) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapearObjetos = mapearObjetos;
  }
  
  @PostConstruct
  public void init() {
    this.listarOrdenesCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName(ConstantesBaseDatos.SP_LISTAR_ORDENES)
        .declareParameters(
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("listaOrdenes", new OrdenesMapper());
    /**
     * 'listaordenes' es el identificador del result set
     */
    
    this.listarFechaIngresoOrdenesCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName(ConstantesBaseDatos.SP_LISTARPOR_FECHAINGRESO)
        .declareParameters(
            new SqlParameter("pa_fechaingreso", Types.VARCHAR),
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("listaOrdenes", new OrdenesMapper());
    
    /**
     * Buscar una orden de servicio
     */
    this.buscarOrdenCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withProcedureName(ConstantesBaseDatos.SP_BUSCAR_ORDENSERVICIO)
        .declareParameters(
            new SqlParameter("pa_idorden", Types.VARCHAR),
            new SqlParameter("pa_ordenfolio", Types.VARCHAR),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR),
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER)
        )
        .returningResultSet("ordenrecuperada", new OrdenesMapper());
    
    this.insertarOrdenCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withProcedureName("SP_INSERTAR_ORDENSERVICIO")
        .declareParameters(
            // IN
            new SqlParameter("pa_idorden", Types.VARCHAR),
            new SqlParameter("pa_clienteid", Types.VARCHAR),
            new SqlParameter("pa_fechaingreso", Types.VARCHAR),
            new SqlParameter("pa_estado", Types.VARCHAR),
            new SqlParameter("pa_totalprendas", Types.VARCHAR),
            new SqlParameter("pa_observaciones", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        );
    
  }
  
  // EJECUCIONES ******************************************************************************************************
  
  /**
   * Listar ordenes de servicio | Inithializer
   *
   * @return
   */
  public Map<String, Object> listarOrdenesCallJdbc() {
    return this.listarOrdenesCall.execute();
  }
  
  /**
   * Listar ordenes de servicio | Inithializer
   *
   * @return
   */
  public Map<String, Object> listarOrdenesFechaIngresoCallJdbc(LocalDate fechaIngreso) {
    Map<String, Object> params = new HashMap<>();
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
    return this.insertarOrdenCall.execute(paramMap);
  }
  
}

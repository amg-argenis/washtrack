package com.washtrack.washtrack_api.orden.respository.inicializador;

import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.orden.rowmapper.OrdenDetalleMapper;
import com.washtrack.washtrack_api.orden.util.MapearObjetosDetalleOrden;
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
public class InicializadorOrdenDetallaSimpJdbcCall {
  
  private SimpleJdbcCall buscarOrdenDetalleCall;
  private SimpleJdbcCall insertarOrdenDetalleCall;
  private SimpleJdbcCall actualizarOrdenDetalleCall;
  private SimpleJdbcCall eliminarOrdenDetalleCall;
  
  private final JdbcTemplate jdbcTemplate;
  private final MapearObjetosDetalleOrden mapearObjetosDetalleOrden;
  
  public InicializadorOrdenDetallaSimpJdbcCall(JdbcTemplate jdbcTemplate,
      MapearObjetosDetalleOrden mapearObjetosDetalleOrden) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapearObjetosDetalleOrden = mapearObjetosDetalleOrden;
  }
  
  @PostConstruct
  public void init() {
    
    // Buscar un detalle de orden
    this.buscarOrdenDetalleCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_BUSCAR_DETALLEORDEN)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_iddetalleorden", Types.VARCHAR),
            new SqlParameter("pa_ordenid", Types.VARCHAR),
            // OUT
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("detalleordenrecuperada", new OrdenDetalleMapper());
    
    // Guardar/Insertar un detalle orden
    this.insertarOrdenDetalleCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_INSERTAR_DETALLEORDEN)
        .declareParameters(
            // IN
            new SqlParameter("pa_idordendetalle", Types.VARCHAR),
            new SqlParameter("pa_ordenid", Types.VARCHAR),
            new SqlParameter("pa_procesoid", Types.VARCHAR),
            new SqlParameter("pa_tipoprenda", Types.VARCHAR),
            new SqlParameter("pa_cantidad", Types.INTEGER),
            new SqlParameter("pa_colorreferencia", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        )
        .returningResultSet("detalleordeninsertar", new OrdenDetalleMapper());  // 👈 agregado
    
    // Actualizar un detalle orden
    this.actualizarOrdenDetalleCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_ACTUALIZAR_DETALLEORDEN)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_idordendetalle", Types.VARCHAR),
            new SqlParameter("pa_ordenid", Types.VARCHAR),
            new SqlParameter("pa_procesoid", Types.VARCHAR),
            new SqlParameter("pa_tipoprenda", Types.VARCHAR),
            new SqlParameter("pa_cantidad", Types.INTEGER),
            new SqlParameter("pa_colorreferencia", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        );
    
    // Eliminar un detalle orden
    this.eliminarOrdenDetalleCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_ELIMINAR_DETALLEORDEN)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_idordendetalle", Types.VARCHAR),
            new SqlParameter("pa_ordenid", Types.VARCHAR),
            // OUT
            new SqlOutParameter("pa_codigobd", Types.INTEGER),
            new SqlOutParameter("pa_mensaje", Types.VARCHAR)
        );
  }
  
  // EJECUCIONES EN BD *************************************************************************************************
  
  /**
   * Buscar una orden detalle de servicio | Inithializer
   *
   * @param detalleOrden
   * @return
   */
  public Map<String, Object> buscarOrdenCallJdbc(DetalleOrdenEntity detalleOrden) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", detalleOrden.getTenantId());
    params.put("pa_iddetalleorden", detalleOrden.getIdDetalleOrden());
    params.put("pa_ordenid", detalleOrden.getOrdenId());
    
    return this.buscarOrdenDetalleCall.execute(params);
  }
  
  /**
   * Guardar una nuevo orden detalle de servicio | Inithializer
   *
   * @param ordenDetalle
   * @return
   */
  public Map<String, Object> insertarDetalleOrden(DetalleOrdenEntity ordenDetalle) {
    Map<String, Object> paramMap = this.mapearObjetosDetalleOrden.parametrizarDetalleOrdenes(ordenDetalle);
    return this.insertarOrdenDetalleCall.execute(paramMap);
  }
  
  /**
   * Actualizar un detalle de orden servicio | Inithializer
   *
   * @param ordenDetalle
   * @return
   */
  public Map<String, Object> actualizarDetalleOrden(DetalleOrdenEntity ordenDetalle) {
    Map<String, Object> paramMap = this.mapearObjetosDetalleOrden.parametrizarDetalleOrdenes(ordenDetalle);
    return this.actualizarOrdenDetalleCall.execute(paramMap);
  }
  
  /**
   * Actualizar un detalle de orden servicio | Inithializer
   *
   * @param ordenDetalle
   * @return
   */
  public Map<String, Object> eliminarDetalleOrden(DetalleOrdenEntity ordenDetalle) {
    Map<String, Object> paramMap = this.mapearObjetosDetalleOrden.parametrizarDetalleOrdenes(ordenDetalle);
    return this.eliminarOrdenDetalleCall.execute(paramMap);
  }
  
}

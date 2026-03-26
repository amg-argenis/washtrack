package com.washtrack.washtrack_api.orden.respository.inicializador;

import com.washtrack.washtrack_api.util.constantes.ConstantesOrdenBaseDatos;
import com.washtrack.washtrack_api.orden.entity.OrdenServicioMasDetallesEntity;
import com.washtrack.washtrack_api.orden.rowmapper.OrdenConDetalleMapper;
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
public class InicializadorOrdenConDetalleSjdbcCall {
  
  private SimpleJdbcCall buscarOrdenDetalleCall;
  
  // Inyeccion de dependencias
  private final JdbcTemplate jdbcTemplate;
  
  public InicializadorOrdenConDetalleSjdbcCall(JdbcTemplate jdbcTemplate,
      MapearObjetosDetalleOrden mapearObjetosDetalleOrden) {
    this.jdbcTemplate = jdbcTemplate;
  }
  
  @PostConstruct
  public void init() {
    this.buscarOrdenDetalleCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesOrdenBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesOrdenBaseDatos.SP_BUSCAR_ORDENSERVICIOCONDETALLE)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_idorden", Types.VARCHAR),
            new SqlParameter("pa_folio", Types.VARCHAR),
            // OUT
            new SqlOutParameter(ConstantesOrdenBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesOrdenBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("detalleconorden", new OrdenConDetalleMapper());
  }
  
  // EJECUCIONES EN BD *************************************************************************************************
  
  /**
   * Buscar una orden detalle de servicio | Inithializer
   *
   * @param detalleOrden
   * @return
   */
  public Map<String, Object> buscarOrdenConDetallesCallJdbc(OrdenServicioMasDetallesEntity detalleOrden) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", "a051a168-fa2a-11f0-aab7-e66133dbb0de"); // hardcodeado por ahora
    params.put("pa_idorden", detalleOrden.getIdOrden());
    params.put("pa_folio", detalleOrden.getFolio());
    
    return this.buscarOrdenDetalleCall.execute(params);
  }
  
}

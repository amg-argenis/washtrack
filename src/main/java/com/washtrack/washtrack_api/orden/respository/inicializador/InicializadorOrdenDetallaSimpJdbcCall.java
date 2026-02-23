package com.washtrack.washtrack_api.orden.respository.inicializador;

import com.washtrack.washtrack_api.orden.constants.ConstantesBaseDatos;
import com.washtrack.washtrack_api.orden.entity.DetalleOrdenEntity;
import com.washtrack.washtrack_api.orden.rowmapper.OrdenDetalleMapper;
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
  
  private final JdbcTemplate jdbcTemplate;
  
  public InicializadorOrdenDetallaSimpJdbcCall(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  
  @PostConstruct
  public void init() {
    this.buscarOrdenDetalleCall = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName(ConstantesBaseDatos.SP_BUSCAR_DETALLEORDEN)
        .declareParameters(
            // IN
            new SqlParameter("pa_iddetalleorden", Types.VARCHAR),
            new SqlParameter("pa_ordenid", Types.VARCHAR),
            // OUT
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("detalleordenrecuperada", new OrdenDetalleMapper());
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
    params.put("pa_iddetalleorden", detalleOrden.getIdDetalleOrden());
    params.put("pa_ordenid", detalleOrden.getOrdenId());
    
    return this.buscarOrdenDetalleCall.execute(params);
  }
}

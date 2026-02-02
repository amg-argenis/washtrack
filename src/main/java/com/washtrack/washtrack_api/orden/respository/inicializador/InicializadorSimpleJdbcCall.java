package com.washtrack.washtrack_api.orden.respository.inicializador;

import com.washtrack.washtrack_api.orden.constants.ConstantesBaseDatos;
import com.washtrack.washtrack_api.orden.constants.ConstantesNumericas;
import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.rowmapper.OrdenesMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InicializadorSimpleJdbcCall {
  
  private SimpleJdbcCall listarOrdenesCall;
  private SimpleJdbcCall buscarOrdenCall;
  
  private final JdbcTemplate jdbcTemplate;
  
  public InicializadorSimpleJdbcCall(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
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
    
    /**
     * Buscar una orden de servicio
     */
    this.buscarOrdenCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withProcedureName("SP_BUSCAR_ORDENSERVICIO")
        .declareParameters(
            new SqlParameter("pa_idorden", Types.VARCHAR),
            new SqlParameter("pa_ordenfolio", Types.VARCHAR),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR),
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER)
        )
        .returningResultSet("ordenrecuperada", new OrdenesMapper());
    
  }
  
  /**
   * Ejecuciones del SimpleJdbcCall
   */
  
  public Map<String, Object> listarOrdenesCallJdbc() {
    return this.listarOrdenesCall.execute();
  }
  
  public Map<String, Object> buscarOrdenCallJdbc(OrdenesEntity orden) {
    
    Map<String, Object> params = new HashMap<>();
    params.put("pa_idorden", orden.getIdOrden());
    params.put("pa_ordenfolio", orden.getFolio());
    
    return this.buscarOrdenCall.execute(params);
  }
  
}

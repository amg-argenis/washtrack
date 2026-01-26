package com.washtrack.washtrack_api.orden.respository.inicializador;

import com.washtrack.washtrack_api.orden.constants.ConstantesBaseDatos;
import com.washtrack.washtrack_api.orden.rowmapper.OrdenesMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.Map;

@Component
public class InicializadorSimpleJdbcCall {
  
  private SimpleJdbcCall simpleJdbcCall;
  
  private final JdbcTemplate jdbcTemplate;
  
  public InicializadorSimpleJdbcCall(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  
  @PostConstruct
  public void init() {
    this.simpleJdbcCall = new SimpleJdbcCall(this.jdbcTemplate)
        .withProcedureName(ConstantesBaseDatos.SP_LISTAR_ORDENES)
        .declareParameters(
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("listaordenes", new OrdenesMapper());
    
    /**
     * 'listaordenes' es el identificador del result set
     */
  }
  
  /**
   * Ejecuciones del SimpleJdbcCall
   */
  
  public Map<String, Object> listarOrdenesCallJdbc() {
    return this.simpleJdbcCall.execute();
  }
  
}

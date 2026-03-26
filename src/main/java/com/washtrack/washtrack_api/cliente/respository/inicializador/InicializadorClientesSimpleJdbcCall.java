package com.washtrack.washtrack_api.cliente.respository.inicializador;

import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import com.washtrack.washtrack_api.cliente.rowmapper.ClientesRowmapper;
import com.washtrack.washtrack_api.util.constantes.ConstantesOrdenBaseDatos;
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
public class InicializadorClientesSimpleJdbcCall {
  
  private SimpleJdbcCall listarClientesSimpleJdbcCall;
  private SimpleJdbcCall buscarClientesSimpleJdbcCall;
  private SimpleJdbcCall insertarClientesSimpleJdbcCall;
  
  private final JdbcTemplate jdbcTemplate;
  
  public InicializadorClientesSimpleJdbcCall(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  
  @PostConstruct
  public void init() {
    this.listarClientesSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesOrdenBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesOrdenBaseDatos.SP_LISTAR_CLIENTES)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            // OUT
            new SqlOutParameter(ConstantesOrdenBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesOrdenBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("listaclientes", new ClientesRowmapper());
    
    this.buscarClientesSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesOrdenBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesOrdenBaseDatos.SP_BUSCAR_CLIENTE)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_idcliente", Types.VARCHAR),
            // OUT
            new SqlOutParameter(ConstantesOrdenBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesOrdenBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("clienterecuperado", new ClientesRowmapper());
    
    this.insertarClientesSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesOrdenBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesOrdenBaseDatos.SP_INSERTAR_CLIENTE)
        .declareParameters(
            // IN
            new SqlParameter("pa_idcliente", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_nombre", Types.VARCHAR),
            new SqlParameter("pa_contacto", Types.VARCHAR),
            new SqlParameter("pa_telefono", Types.VARCHAR),
            new SqlParameter("pa_email", Types.VARCHAR),
            new SqlParameter("pa_creditohabilitado", Types.TINYINT),
            new SqlParameter("pa_limitecredito", Types.VARCHAR),
            // OUT
            new SqlOutParameter(ConstantesOrdenBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesOrdenBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        );
    
  }
  
  // EJECUCIONES EN BD *************************************************************************************************
  
  /**
   * Listar ordenes de servicio | Inithializer
   *
   * @return
   */
  public Map<String, Object> listarClientesCallJdbcMethod(String tenantId) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", tenantId);
    return this.listarClientesSimpleJdbcCall.execute(params);
  }
  
  /**
   * Buscar un cliente
   *
   * @param idCliente
   * @param tenantId
   * @return
   */
  public Map<String, Object> buscarClientesCallJdbcMethod(String idCliente, String tenantId) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", "a051a168-fa2a-11f0-aab7-e66133dbb0de"); // Temporal ******
    params.put("pa_idcliente", idCliente);
    return this.buscarClientesSimpleJdbcCall.execute(params);
  }
  
  /**
   * Insertar un cliente
   *
   * @param clientesEntity
   * @return
   */
  public Map<String, Object> insertarClientesCallJdbcMethod(ClientesEntity clientesEntity) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", "a051a168-fa2a-11f0-aab7-e66133dbb0de"); // Temporal ******
    params.put("pa_idcliente", clientesEntity.getIdCliente());
    params.put("pa_nombre", clientesEntity.getNombre());
    params.put("pa_contacto", clientesEntity.getContacto());
    params.put("pa_telefono", clientesEntity.getTelefono());
    params.put("pa_email", clientesEntity.getEmail());
    params.put("pa_creditohabilitado", clientesEntity.isCreditoHabilitado());
    params.put("pa_limitecredito", clientesEntity.getLimiteCredito());
    
    return this.insertarClientesSimpleJdbcCall.execute(params);
  }
  
}

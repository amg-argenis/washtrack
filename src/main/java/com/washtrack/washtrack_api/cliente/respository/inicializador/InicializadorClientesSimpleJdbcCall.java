package com.washtrack.washtrack_api.cliente.respository.inicializador;

import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import com.washtrack.washtrack_api.cliente.rowmapper.ClientesRowmapper;
import com.washtrack.washtrack_api.cliente.util.MapearObjetosCliente;
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
public class InicializadorClientesSimpleJdbcCall {
  
  private SimpleJdbcCall listarClientesSimpleJdbcCall;
  private SimpleJdbcCall buscarClientesSimpleJdbcCall;
  private SimpleJdbcCall insertarClientesSimpleJdbcCall;
  private SimpleJdbcCall actualizarClientesSimpleJdbcCall;
  private SimpleJdbcCall eliminarClientesSimpleJdbcCall;
  
  private final JdbcTemplate jdbcTemplate;
  private final MapearObjetosCliente mapearObjetosCliente;
  
  public InicializadorClientesSimpleJdbcCall(JdbcTemplate jdbcTemplate, MapearObjetosCliente mapearObjetosCliente) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapearObjetosCliente = mapearObjetosCliente;
  }
  
  @PostConstruct
  public void init() {
    
    // Listar los clientes por Tenant Id
    this.listarClientesSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_LISTAR_CLIENTES)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            // OUT
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("listaclientes", new ClientesRowmapper());
    
    // Buscar cliente por Tenant Id y Id cliente
    this.buscarClientesSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_BUSCAR_CLIENTE)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_idcliente", Types.VARCHAR),
            // OUT
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("clienterecuperado", new ClientesRowmapper());
    
    // Insertar un nuevo cliente por Tenant Id
    this.insertarClientesSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_INSERTAR_CLIENTE)
        .declareParameters(
            // IN
            new SqlParameter("pa_idcliente", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_nombre", Types.VARCHAR),
            new SqlParameter("pa_contacto", Types.VARCHAR),
            new SqlParameter("pa_telefono", Types.VARCHAR),
            new SqlParameter("pa_email", Types.VARCHAR),
            new SqlParameter("pa_creditohabilitado", Types.TINYINT),
            new SqlParameter("pa_limitecredito", Types.DECIMAL),
            // OUT
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("clienterecuperado", new ClientesRowmapper());
    
    // Actualizar cliente por Tenant Id y Id cliente
    this.actualizarClientesSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_ACTUALIZAR_CLIENTE)
        .declareParameters(
            // IN
            new SqlParameter("pa_idcliente", Types.VARCHAR),
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_nombre", Types.VARCHAR),
            new SqlParameter("pa_contacto", Types.VARCHAR),
            new SqlParameter("pa_telefono", Types.VARCHAR),
            new SqlParameter("pa_email", Types.VARCHAR),
            new SqlParameter("pa_creditohabilitado", Types.TINYINT),
            new SqlParameter("pa_limitecredito", Types.DECIMAL),
            // OUT
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR)
        )
        .returningResultSet("clienterecuperado", new ClientesRowmapper());
    
    // Eliminar cliente por Tenant Id y Id cliente
    this.eliminarClientesSimpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName(ConstantesBaseDatos.WASHTRACKDB)
        .withProcedureName(ConstantesBaseDatos.SP_ELIMINAR_CLIENTE)
        .declareParameters(
            // IN
            new SqlParameter("pa_tenantid", Types.VARCHAR),
            new SqlParameter("pa_idcliente", Types.VARCHAR),
            // OUT
            new SqlOutParameter(ConstantesBaseDatos.CODIGOBD, Types.INTEGER),
            new SqlOutParameter(ConstantesBaseDatos.PAMENSAJEBD, Types.VARCHAR)
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
    params.put("pa_tenantid", tenantId);
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
    Map<String, Object> params = this.mapearObjetosCliente.parametrizarObjetoClienteEntity(clientesEntity);
    return this.insertarClientesSimpleJdbcCall.execute(params);
  }
  
  /**
   * Actualizar un cliente
   *
   * @param clientesEntity
   * @return
   */
  public Map<String, Object> actualizarClientesCallJdbcMethod(ClientesEntity clientesEntity) {
    Map<String, Object> params = this.mapearObjetosCliente.parametrizarObjetoClienteEntity(clientesEntity);
    return this.actualizarClientesSimpleJdbcCall.execute(params);
  }
  
  /**
   * Eliminar un cliente
   *
   * @param idCliente
   * @return
   */
  public Map<String, Object> eliminarClientesCallJdbcMethod(String idCliente, String tenantId) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", tenantId);
    params.put("pa_idcliente", idCliente);
    return this.eliminarClientesSimpleJdbcCall.execute(params);
  }
  
}

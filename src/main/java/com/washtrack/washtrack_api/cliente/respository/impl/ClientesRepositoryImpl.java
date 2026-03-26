package com.washtrack.washtrack_api.cliente.respository.impl;

import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import com.washtrack.washtrack_api.cliente.respository.IClientesRepository;
import com.washtrack.washtrack_api.cliente.respository.inicializador.InicializadorClientesSimpleJdbcCall;
import com.washtrack.washtrack_api.util.constantes.ConstantesOrdenBaseDatos;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class ClientesRepositoryImpl implements IClientesRepository {
  
  private final InicializadorClientesSimpleJdbcCall inicializadorClientesSimpleJdbcCall;
  
  public ClientesRepositoryImpl(InicializadorClientesSimpleJdbcCall inicializadorClientesSimpleJdbcCall) {
    this.inicializadorClientesSimpleJdbcCall = inicializadorClientesSimpleJdbcCall;
  }
  
  @Override
  public List<ClientesEntity> listarClientesRepository(String tenantId) {
    log.info("[Inicia listar clientes | Repository]");
    
    List<ClientesEntity> lista = new ArrayList<>();
    Map<String, Object> resultado;
    try {
      // Ejecucion
      resultado = this.inicializadorClientesSimpleJdbcCall.listarClientesCallJdbcMethod(tenantId);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesOrdenBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesOrdenBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("El SP no devolvio pa_codigobd, se asume error.");
      }
      else {
        lista = (List<ClientesEntity>) resultado.get("listaclientes");
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al obtener listado de clientes desde la BD "
              + "| Repository | Mas detalles: {}]", e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al listar los clientes en la BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza listar los clientes | Repository]");
    }
    
    // ResultSet
    return lista;
  }
  
  @Override
  public ClientesEntity buscarClienteRepository(ClientesEntity cliente) {
    log.info("[Inicia buscar cliente | Repository]");
    
    try {
      Map<String, Object> resultado = this.inicializadorClientesSimpleJdbcCall.buscarClientesCallJdbcMethod(
          cliente.getIdCliente(), cliente.getTenantId());
      
      Integer codigobd =
          (Integer) resultado.get(ConstantesOrdenBaseDatos.CODIGOBD);
      String pamensaje =
          (String) resultado.get(ConstantesOrdenBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        List<ClientesEntity> lista =
            (List<ClientesEntity>) resultado.get("clienterecuperado");
        
        if ( lista != null && !lista.isEmpty() ) {
          return lista.get(ConstantesNumericas.CERO);
        }
      }
      
      return null;
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al buscar el cliente en BD]", e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al buscar el cliente en la BD | Repository]: {}", e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza buscar cliente | Repository]");
    }
  }
  
  @Override
  public ClientesEntity insertarClienteRepository(ClientesEntity cliente) {
    log.info("[Inicia insertar nuevo cliente | Repository]");
    ClientesEntity clientesEntity = null;
    
    try {
      // Ejecucion
      Map<String, Object> resultado = this.inicializadorClientesSimpleJdbcCall.insertarClientesCallJdbcMethod(cliente);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesOrdenBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesOrdenBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        List<ClientesEntity> entityList =
            (List<ClientesEntity>) resultado.get("clienterecuperado");
        clientesEntity = entityList.get(ConstantesNumericas.CERO);
      }
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Hubo un problema al insertar el nuevo cliente | Repository | Mas detalles: {}]", pamensaje);
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al insertar el nuevo cliente en BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al insertar el nuevo cliente en la BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza insertar nuevo cliente | Repository]");
    }
    
    return clientesEntity;
  }
  
  @Override
  public ClientesEntity actualizarClienteRepository(ClientesEntity cliente) {
    log.info("[Inicia actualizar cliente | Repository]");
    
    ClientesEntity clientesEntity = null;
    
    try {
      // Ejecucion
      Map<String, Object> resultado =
          this.inicializadorClientesSimpleJdbcCall.actualizarClientesCallJdbcMethod(cliente);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesOrdenBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesOrdenBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        List<ClientesEntity> entityList =
            (List<ClientesEntity>) resultado.get("clienterecuperado");
        clientesEntity = entityList.getFirst();
      }
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP no devolvio pa_codigobd, se asume error]");
      }
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al actualizar el cliente en BD | Repository | Mas detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error al actualizar el cliente en BD | Repository]: {}", e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza actualizar cliente | Repository]");
    }
    
    return clientesEntity;
  }
  
  @Override
  public Integer eliminarClienteRepository(ClientesEntity cliente) {
    return 0;
  }
}

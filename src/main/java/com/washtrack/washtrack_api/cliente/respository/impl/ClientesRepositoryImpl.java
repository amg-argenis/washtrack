package com.washtrack.washtrack_api.cliente.respository.impl;

import com.washtrack.washtrack_api.cliente.dto.ClienteBuscarEliminarRequest;
import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import com.washtrack.washtrack_api.cliente.response.ResponseClientesRepository;
import com.washtrack.washtrack_api.cliente.respository.IClientesRepository;
import com.washtrack.washtrack_api.cliente.respository.inicializador.InicializadorClientesSimpleJdbcCall;
import com.washtrack.washtrack_api.proceso.entity.ProcesosEntity;
import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
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
  public ResponseClientesRepository listarClientesRepository(String tenantId) {
    log.info("[Inicia listar clientes | Repository]");
    
    ResponseClientesRepository responseRepository = new ResponseClientesRepository();
    Map<String, Object> resultado;
    try {
      // Ejecucion
      resultado = this.inicializadorClientesSimpleJdbcCall.listarClientesCallJdbcMethod(tenantId);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setClientes(null);
      responseRepository.setEntityList(new ArrayList<>());
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP listar clientes fallo, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<ClientesEntity> lista = (List<ClientesEntity>) resultado.get("listaclientes");
        responseRepository.setEntityList(lista);
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Registros de clientes No encontrados en la BD | Repository]");
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
    return responseRepository;
  }
  
  @Override
  public ResponseClientesRepository buscarClienteRepository(String idCliente, String tenantId) {
    log.info("[Inicia buscar cliente | Repository]");
    
    ResponseClientesRepository responseRepository = new ResponseClientesRepository();
    
    try {
      Map<String, Object> resultado =
          this.inicializadorClientesSimpleJdbcCall.buscarClientesCallJdbcMethod(idCliente, tenantId);
      
      Integer codigobd =
          (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje =
          (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setClientes(null);
      responseRepository.setEntityList(new ArrayList<>());
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para buscar cliente no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        List<ClientesEntity> lista =
            (List<ClientesEntity>) resultado.get("clienterecuperado");
        
        if ( lista != null && !lista.isEmpty() ) {
          responseRepository.setClientes(lista.get(ConstantesNumericas.CERO));
        }
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Registros de clientes No encontrados en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al buscar el cliente en BD | Repository]", e);
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
    return responseRepository;
  }
  
  @Override
  public ResponseClientesRepository insertarClienteRepository(ClientesEntity cliente) {
    log.info("[Inicia insertar nuevo cliente | Repository]");
    
    ResponseClientesRepository responseRepository = new ResponseClientesRepository();
    
    try {
      // Ejecucion
      log.info("[Cliente a insertar: {}]", cliente);
      Map<String, Object> resultado = this.inicializadorClientesSimpleJdbcCall.insertarClientesCallJdbcMethod(cliente);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setClientes(null);
      responseRepository.setEntityList(new ArrayList<>());
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        List<ClientesEntity> entityList =
            (List<ClientesEntity>) resultado.get("clienterecuperado");
        if ( entityList != null && !entityList.isEmpty() ) {
          responseRepository.setClientes(entityList.get(ConstantesNumericas.CERO));
        }
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
    
    return responseRepository;
  }
  
  @Override
  public ResponseClientesRepository actualizarClienteRepository(ClientesEntity cliente) {
    log.info("[Inicia actualizar cliente | Repository]");
    
    ResponseClientesRepository responseRepository = new ResponseClientesRepository();
    
    try {
      // Ejecucion
      log.info("[Cliente a editar: {}]", cliente);
      Map<String, Object> resultado =
          this.inicializadorClientesSimpleJdbcCall.actualizarClientesCallJdbcMethod(cliente);
      
      // OUT parameter seguro
      Integer codigobd = (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje = (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setClientes(null);
      responseRepository.setEntityList(new ArrayList<>());
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        List<ClientesEntity> entityList =
            (List<ClientesEntity>) resultado.get("clienterecuperado");
        if ( entityList != null && !entityList.isEmpty() ) {
          responseRepository.setClientes(entityList.get(ConstantesNumericas.CERO));
        }
      }
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para actualizar cliente no devolvio pa_codigobd, se asume error]");
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
    
    return responseRepository;
  }
  
  @Override
  public Integer eliminarClienteRepository(ClienteBuscarEliminarRequest clienteDto) {
    log.info("[Inicia eliminar cliente | Repository]");
    
    Integer codigobd;
    
    try {
      Map<String, Object> resultado = this.inicializadorClientesSimpleJdbcCall.eliminarClientesCallJdbcMethod(
          clienteDto.getIdCliente(), clienteDto.getTenantId());
      
      codigobd =
          (Integer) resultado.get(ConstantesBaseDatos.CODIGOBD);
      String pamensaje =
          (String) resultado.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, pamensaje);
      
      if ( codigobd == null || codigobd == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para eliminar cliente no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd == ConstantesNumericas.CERO ) {
        log.info("[Cliente eliminado correctamente de la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error al eliminar el cliente de la BD | Detalles: {}]", e, e.getMessage());
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al eliminar el cliente de la BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza eliminar cliente | Repository]");
    }
    
    return codigobd;
  }
  
}

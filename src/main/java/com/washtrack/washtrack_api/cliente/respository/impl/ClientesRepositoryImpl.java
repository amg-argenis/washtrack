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
    return null;
  }
  
  @Override
  public ClientesEntity insertarClienteRepository(ClientesEntity cliente) {
    return null;
  }
  
  @Override
  public Integer actualizarClienteRepository(ClientesEntity cliente) {
    return 0;
  }
  
  @Override
  public Integer eliminarClienteRepository(ClientesEntity cliente) {
    return 0;
  }
}

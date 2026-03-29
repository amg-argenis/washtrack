package com.washtrack.washtrack_api.usuarios.repository.impl;

import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import com.washtrack.washtrack_api.usuarios.repository.IUsuarioRepository;
import com.washtrack.washtrack_api.usuarios.repository.inicializador.InicializadorRepositoryUsuarios;
import com.washtrack.washtrack_api.util.constantes.ConstantesBaseDatos;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class UsuarioRepositoryImpl implements IUsuarioRepository {
  
  private final InicializadorRepositoryUsuarios inicializadorRepositoryUsuarios;
  
  public UsuarioRepositoryImpl(InicializadorRepositoryUsuarios inicializadorRepositoryUsuarios) {
    this.inicializadorRepositoryUsuarios = inicializadorRepositoryUsuarios;
  }
  
  @Override
  public UsuarioEntity consultarUsuarioLogInRepository(String email, String password) {
    
    log.info("[Inicia login de usuario | Repository]");
    
    UsuarioEntity loginResponse = null;
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.logInUsuarioJdbcMethod(email, password);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para login usuario no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<UsuarioEntity> usuarioEntity = (List<UsuarioEntity>) respuesta.get("usuariologinrec");
        loginResponse = usuarioEntity.get(ConstantesNumericas.CERO);
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Usuario no encontrado para login en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al consultar usuario para login en BD | Repository"
          + " | Detalles: {}]", e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al consultar usuario para login en BD | Repository "
          + " | Detalles: {}]", e.getMessage(), e);
      throw e;
    }
    finally {
      log.info("[Finaliza login de usuario | Repository]");
    }
    
    return loginResponse;
  }
  
  @Override
  public UsuarioEntity buscarUsuarioPorIdRepository(String idUsuario) {
    log.info("[Inicia buscar usuario | Repository]");
    
    UsuarioEntity loginResponse = null;
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.buscarUsuarioPorIdJdbcMethod(idUsuario);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para busqueda de usuario no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<UsuarioEntity> usuarioEntity = (List<UsuarioEntity>) respuesta.get("usuariorecuperado");
        log.info("[Usuario encontrado: {}]", usuarioEntity.get(ConstantesNumericas.CERO));
        loginResponse = usuarioEntity.get(ConstantesNumericas.CERO);
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Usuario no encontrado en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al consultar usuarioen BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al consultar usuario en BD | Repository | Detalles: {}]", e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza usuario | Repository]");
    }
    
    return loginResponse;
  }
  
  @Override
  public UsuarioEntity insertarUsuarioRepository(UsuarioEntity usuario) {
    return null;
  }
  
  @Override
  public Integer eliminarUsuarioRepository(UsuarioEntity usuario) {
    return 0;
  }
  
  @Override
  public List<UsuarioEntity> listarUsuariosRepository() {
    log.info("[Inicia listar usuarios | Repository]");
    
    List<UsuarioEntity> entityList = null;
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.listarUsuariosJdbcMethod();
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para listar usuarios no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        entityList = (List<UsuarioEntity>) respuesta.get("listausuarios");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[No hay registros de usuarios aun en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al consultar usuarios en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al consultar usuarios en BD | Repository | Detalles: {}]", e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza listar usuarios | Repository]");
    }
    
    return entityList;
  }
  
  @Override
  public Integer actualizarUsuarioRepository(UsuarioEntity usuario) {
    return 0;
  }
  
}

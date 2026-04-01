package com.washtrack.washtrack_api.usuarios.repository.impl;

import com.washtrack.washtrack_api.usuarios.response.UsuarioResponseRepository;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioInsertEntity;
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
  public UsuarioResponseRepository consultarUsuarioLogInRepository(String email, String password) {
    
    log.info("[Inicia login de usuario | Repository]");
    
    UsuarioResponseRepository loginResponse = new UsuarioResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.logInUsuarioJdbcMethod(email, password);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      loginResponse.setUsuarioEntity(null);
      loginResponse.setCodigobd(codigobd);
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para login usuario no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<UsuarioEntity> usuarioEntity = (List<UsuarioEntity>) respuesta.get("usuariologinrec");
        loginResponse.setUsuarioEntity(usuarioEntity.get(ConstantesNumericas.CERO));
        loginResponse.setCodigobd(codigobd);
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
  public UsuarioResponseRepository buscarUsuarioPorIdRepository(String idUsuario) {
    log.info("[Inicia buscar usuario | Repository]");
    
    UsuarioResponseRepository responseRepository = new UsuarioResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.buscarUsuarioPorIdJdbcMethod(idUsuario);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setUsuarioEntity(null);
      responseRepository.setCodigobd(codigobd);
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para busqueda de usuario no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<UsuarioEntity> usuarioEntityList = (List<UsuarioEntity>) respuesta.get("usuariorecuperado");
        UsuarioEntity usuarioEntity = usuarioEntityList.get(ConstantesNumericas.CERO);
        log.info("[Usuario encontrado: {}]", usuarioEntity);
        responseRepository.setUsuarioEntity(usuarioEntity);
        responseRepository.setCodigobd(codigobd);
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Usuario no encontrado en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al consultar usuario en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al consultar usuario en BD | Repository | Detalles: {}]", e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza buscar usuario | Repository]");
    }
    
    return responseRepository;
  }
  
  @Override
  public UsuarioResponseRepository buscarUsuarioPorEmailRepository(String email, String tenantId) {
    log.info("[Inicia buscar usuario por email | Repository]");
    
    UsuarioResponseRepository responseRepository = new UsuarioResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.buscarUsuarioPorEmailJdbcMethod(email, tenantId);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setUsuarioEntity(null);
      responseRepository.setCodigobd(codigobd);
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para busqueda de usuario por email no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<UsuarioEntity> usuarioEntityList = (List<UsuarioEntity>) respuesta.get("usuariorecuperado");
        UsuarioEntity usuarioEntity = usuarioEntityList.get(ConstantesNumericas.CERO);
        log.info("[Usuario encontrado por email: {}]", usuarioEntity);
        responseRepository.setUsuarioEntity(usuarioEntity);
        responseRepository.setCodigobd(codigobd);
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[Usuario no encontrado por email en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al consultar usuario por email en la BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al consultar usuario por email en BD | Repository | Detalles: {}]",
          e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza buscar usuario por email | Repository]");
    }
    
    return responseRepository;
  }
  
  @Override
  public UsuarioResponseRepository insertarUsuarioRepository(UsuarioInsertEntity usuarioInsert) {
    log.info("[Inicia insertar usuario | Repository]");
    
    UsuarioResponseRepository responseRepository = new UsuarioResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.insertarUsuarioJdbcMethod(usuarioInsert);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setUsuarioEntity(null);
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<UsuarioEntity> usuarioEntityList = (List<UsuarioEntity>) respuesta.get("usuarioinsertado");
        UsuarioEntity usuarioEntity = usuarioEntityList.get(ConstantesNumericas.CERO);
        responseRepository.setUsuarioEntity(usuarioEntity);
        responseRepository.setCodigobd(codigobd);
        log.info("[Usuario insertado correctamente en la BD: {}]", usuarioEntity);
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para insert de usuario no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[El usuario a insertar ya existe en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al insertar usuario en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al insertar usuario en BD | Repository | Detalles: {}]", e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza insertar usuario | Repository]");
    }
    
    return responseRepository;
  }
  
  @Override
  public Integer eliminarUsuarioRepository(String idUsuario, String email, String tenantId) {
    log.info("[Inicia eliminar usuario | Repository]");
    
    Integer codigobd;
    
    log.info("[Usuario a eliminar de la BD: (Id: {} | Email: {} | TenantId: {})]", idUsuario, email,
        tenantId);
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.eliminarUsuarioJdbcMethod(idUsuario, email, tenantId);
      
      codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        log.info("[Usuario eliminado correctamente de la BD");
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para eliminar el usuario fallo, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[El usuario a eliminar no existe o esta inactivo en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al eliminar el usuario de la BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al eliminar el usuario de la BD | Repository | Detalles: {}]",
          e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza eliminar usuario | Repository]");
    }
    
    return codigobd;
  }
  
  @Override
  public UsuarioResponseRepository reactivarUsuarioRepository(String email, String tenantId) {
    log.info("[Inicia reactivar usuario | Repository]");
    
    UsuarioResponseRepository usuarioEntity = new UsuarioResponseRepository();
    
    log.info("[Usuario a reactivar en la BD: (Email: {} | TenantId: {})]", email, tenantId);
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.reactivarUsuarioJdbcMethod(email, tenantId);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      usuarioEntity.setUsuarioEntity(null);
      usuarioEntity.setCodigobd(codigobd);
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        log.info("[Usuario reactivado correctamente en la BD");
        List<UsuarioEntity> usuarioEntityList = (List<UsuarioEntity>) respuesta.get("usuarioreactivado");
        usuarioEntity.setUsuarioEntity(usuarioEntityList.get(ConstantesNumericas.CERO));
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para reactivar el usuario fallo, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[El usuario a reactivar no existe en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al reactivar el usuario de la BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al reactivar el usuario de la BD | Repository | Detalles: {}]",
          e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza reactivar usuario | Repository]");
    }
    
    return usuarioEntity;
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
  public List<UsuarioEntity> listarUsuariosPorTenantIdRepository(String tenantId) {
    log.info("[Inicia listar usuarios por tenant Id | Repository]");
    
    List<UsuarioEntity> entityList = null;
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.listarUsuariosJdbcMethod();
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para listar usuarios por tenant Id no devolvio pa_codigobd, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        entityList = (List<UsuarioEntity>) respuesta.get("listausuarios");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[No hay registros de usuarios por tenant Id en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error critico al consultar usuarios por tenant Id en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al consultar usuarios por tenant Id en BD | Repository | Detalles: {}]",
          e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza listar usuarios por tenant Id | Repository]");
    }
    
    return entityList;
  }
  
  @Override
  public UsuarioResponseRepository actualizarUsuarioRepository(UsuarioInsertEntity usuario) {
    log.info("[Inicia actualizar usuario | Repository]");
    
    UsuarioResponseRepository responseRepository = new UsuarioResponseRepository();
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.actualizarUsuarioJdbcMethod(usuario);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      responseRepository.setCodigobd(codigobd);
      responseRepository.setUsuarioEntity(null);
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.CERO ) {
        List<UsuarioEntity> usuarioEntityList = (List<UsuarioEntity>) respuesta.get("usuarioactualizado");
        UsuarioEntity usuarioEntity = usuarioEntityList.get(ConstantesNumericas.CERO);
        responseRepository.setUsuarioEntity(usuarioEntity);
        responseRepository.setCodigobd(codigobd);
        log.info("[Usuario actualizado correctamente en la BD: {}]", usuarioEntity);
      }
      
      if ( codigobd == null || codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.warn("[El SP para actualizar el usuario fallo, se asume error]");
      }
      
      if ( codigobd != null && codigobd.intValue() == ConstantesNumericas.DOS ) {
        log.warn("[El usuario a actualziar no existe en la BD | Repository]");
      }
      
    }
    catch ( DataAccessException e ) {
      log.error("[DataAccessException | Error critico al actualizar usuario en BD | Repository | Detalles: {}]",
          e.getMessage(), e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al actualizar usuario en BD | Repository | Detalles: {}]", e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza actualizar usuario | Repository]");
    }
    
    return responseRepository;
  }
  
}

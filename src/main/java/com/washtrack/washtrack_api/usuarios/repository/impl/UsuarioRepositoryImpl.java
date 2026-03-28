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
    
    log.info("[Inicia consulta de usuario | Repository]");
    
    UsuarioEntity loginResponse = null;
    
    try {
      Map<String, Object> respuesta =
          this.inicializadorRepositoryUsuarios.logInUsuarioJdbcMethod(email, password);
      
      Integer codigobd = (Integer) respuesta.get(ConstantesBaseDatos.CODIGOBD);
      String mensajebd = (String) respuesta.get(ConstantesBaseDatos.PAMENSAJEBD);
      
      log.info("[Repository | Respuesta BD, Codigo: {} | Mensaje: {}]", codigobd, mensajebd);
      
      if ( codigobd == null && codigobd.intValue() == ConstantesNumericas.UNONEGATIVO ) {
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
      log.error("[DataAccessException | Error critico al consultar usuario para login en BD | Repository]", e);
      throw e;
    }
    catch ( Exception e ) {
      log.error("[Exception | Error critico al consultar usuario para login en BD | Repository]: {}", e.getMessage(),
          e);
      throw e;
    }
    finally {
      log.info("[Finaliza consulta de usuario | Repository]");
    }
    
    return loginResponse;
  }
  
  @Override
  public UsuarioEntity buscarUsuarioPorIdRepository(String idUsuario) {
    return null;
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
    return List.of();
  }
  
  @Override
  public Integer actualizarUsuarioRepository(UsuarioEntity usuario) {
    return 0;
  }
  
}

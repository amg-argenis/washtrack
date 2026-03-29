package com.washtrack.washtrack_api.usuarios.service.impl;

import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.util.MapearRespuestasConsultas;
import com.washtrack.washtrack_api.usuarios.dto.LoginRequest;
import com.washtrack.washtrack_api.usuarios.dto.LoginResponse;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import com.washtrack.washtrack_api.usuarios.repository.IUsuarioRepository;
import com.washtrack.washtrack_api.usuarios.service.IUsuarioService;
import com.washtrack.washtrack_api.usuarios.util.MapearObjetosUsuario;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsuarioServiceImpl implements IUsuarioService {
  
  private final IUsuarioRepository usuarioRepository;
  private final MapearRespuestasConsultas mapearRespuestasConsultas;
  private final MapearObjetosUsuario mapearObjetosUsuario;
  
  public UsuarioServiceImpl(IUsuarioRepository usuarioRepository, MapearRespuestasConsultas mapearRespuestasConsultas,
      MapearObjetosUsuario mapearObjetosUsuario) {
    this.usuarioRepository = usuarioRepository;
    this.mapearRespuestasConsultas = mapearRespuestasConsultas;
    this.mapearObjetosUsuario = mapearObjetosUsuario;
  }
  
  @Override
  public ServiceResult<Object> consultarUsuarioLogInService(LoginRequest loginRequest) {
    log.info("[Inicia buscar usuario login | Service]");
    
    log.info("[Login request: ({}|{}) | Service]", loginRequest.getEmail(), loginRequest.getPassword());
    
    ServiceResult<Object> serviceResult;
    
    try {
      // Llamada al Repository
      UsuarioEntity resultado =
          this.usuarioRepository.consultarUsuarioLogInRepository(loginRequest.getEmail(), loginRequest.getPassword());
      
      if ( resultado == null ) {
        log.info("[Usuario para login no encontrado | Service]");
        return this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      // Mapear Entity → DTO (respuesta)
      LoginResponse loginResponse = this.mapearObjetosUsuario.toDtoLoginUsuarioMapper(resultado);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
          ConstantesOrdenes.OPERACION_EXITOSA,
          ConstantesNumericas.UNO, loginResponse
      );
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al buscar el usuario login "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al buscar el usuario login | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    
    log.info("[Finaliza buscar usuario login | Service]");
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> buscarUsuarioPorIdService(String idUsuario) {
    log.info("[Inicia buscar usuario | Service]");
    
    log.info("[Usuario a buscar: ({}) | Service]", idUsuario);
    
    ServiceResult<Object> serviceResult;
    
    try {
      // Llamada al Repository
      UsuarioEntity resultado =
          this.usuarioRepository.buscarUsuarioPorIdRepository(idUsuario);
      
      if ( resultado == null ) {
        log.info("[Usuario no encontrado | Service]");
        return this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      // Mapear Entity → DTO (respuesta)
      LoginResponse loginResponse = this.mapearObjetosUsuario.toDtoLoginUsuarioMapper(resultado);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
          ConstantesOrdenes.OPERACION_EXITOSA,
          ConstantesNumericas.UNO, loginResponse
      );
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al buscar el usuario "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al buscar el usuario | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    
    log.info("[Finaliza buscar usuario | Service]");
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> insertarUsuarioService(UsuarioEntity usuario) {
    return null;
  }
  
  @Override
  public ServiceResult<Object> eliminarUsuarioService(UsuarioEntity usuario) {
    return null;
  }
  
  @Override
  public ServiceResult<Object> listarUsuariosService() {
    log.info("[Inicia listar usuarios | Service]");
    
    ServiceResult<Object> serviceResult;
    
    try {
      // Llamada al Repository
      List<UsuarioEntity> resultado =
          this.usuarioRepository.listarUsuariosRepository();
      
      if ( resultado == null || resultado.isEmpty() ) {
        log.info("[No hay registro de usuarios en la BD | Service]");
        return this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      // Mapear Entity → DTO (respuesta)
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
          ConstantesOrdenes.OPERACION_EXITOSA,
          resultado.size(), resultado
      );
    }
    catch ( NullPointerException e ) {
      log.error("[NullPointerException | Error critico, alguno de los datos es NULL | Service |  Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    catch ( DataAccessException e ) {
      log.error(
          "[DataAccessException | Error al listar usuarios "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al listar usuarios | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    
    log.info("[Finaliza listar usuarios | Service]");
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> actualizarUsuarioService(UsuarioEntity usuario) {
    return null;
  }
  
}

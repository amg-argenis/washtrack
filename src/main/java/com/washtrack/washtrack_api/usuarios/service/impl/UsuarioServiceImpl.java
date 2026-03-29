package com.washtrack.washtrack_api.usuarios.service.impl;

import com.washtrack.washtrack_api.orden.constants.ConstantesOrdenes;
import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.orden.util.MapearRespuestasConsultas;
import com.washtrack.washtrack_api.usuarios.dto.LoginUsuarioRequest;
import com.washtrack.washtrack_api.usuarios.dto.LoginUsuarioResponse;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioEliminarReactivarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioInsertDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioResponseRepository;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioInsertEntity;
import com.washtrack.washtrack_api.usuarios.repository.IUsuarioRepository;
import com.washtrack.washtrack_api.usuarios.service.IUsuarioService;
import com.washtrack.washtrack_api.usuarios.util.MapearObjetosUsuario;
import com.washtrack.washtrack_api.util.constantes.ConstantesNumericas;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
  public ServiceResult<Object> consultarUsuarioLogInService(LoginUsuarioRequest loginUsuarioRequest) {
    log.info("[Inicia buscar usuario login | Service]");
    
    log.info("[Login request: ({}|{}) | Service]", loginUsuarioRequest.getEmail(), loginUsuarioRequest.getPassword());
    
    ServiceResult<Object> serviceResult;
    
    try {
      // Llamada al Repository
      UsuarioEntity resultado =
          this.usuarioRepository.consultarUsuarioLogInRepository(loginUsuarioRequest.getEmail(),
              loginUsuarioRequest.getPassword());
      
      if ( resultado == null ) {
        log.info("[Usuario para login no encontrado | Service]");
        return this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.SIN_REGISTROS,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      // Mapear Entity → DTO (respuesta)
      LoginUsuarioResponse loginUsuarioResponse = this.mapearObjetosUsuario.toDtoLoginUsuarioMapper(resultado);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
          ConstantesOrdenes.OPERACION_EXITOSA,
          ConstantesNumericas.UNO, loginUsuarioResponse
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
    finally {
      log.info("[Finaliza buscar usuario login | Service]");
    }
    
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
      LoginUsuarioResponse loginUsuarioResponse = this.mapearObjetosUsuario.toDtoLoginUsuarioMapper(resultado);
      serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
          ConstantesOrdenes.OPERACION_EXITOSA,
          ConstantesNumericas.UNO, loginUsuarioResponse
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
    finally {
      log.info("[Finaliza buscar usuario | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> insertarUsuarioService(UsuarioInsertDto usuarioInsertDto) {
    log.info("[Inicia insertar usuario | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      // Mapear DTO → Entity (request)
      UsuarioInsertEntity usuarioEntity = this.mapearObjetosUsuario.fromDtoToEntityUsuarioMapper(usuarioInsertDto);
      
      // UUID para idUsuario
      UUID uuid = UUID.randomUUID();
      usuarioEntity.setIdUsuario(uuid.toString());
      log.info("[Usuario a insertar: ({}) | Service]", usuarioEntity);
      
      // Llamada al Repository
      UsuarioResponseRepository resultado =
          this.usuarioRepository.insertarUsuarioRepository(usuarioEntity);
      
      // Mapear Entity → DTO (response)
      if ( resultado.getUsuarioEntity() != null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.CERO ) {
        LoginUsuarioResponse loginUsuarioResponse =
            this.mapearObjetosUsuario.toDtoLoginUsuarioMapper(resultado.getUsuarioEntity());
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesOrdenes.OPERACION_EXITOSA,
            ConstantesNumericas.UNO, loginUsuarioResponse
        );
      }
      
      if ( resultado.getUsuarioEntity() == null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Usuario no insertado en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.ERROR_INSERT,
            ApiErrorCode.ERROR_BASE_DATOS
        );
      }
      
      if ( resultado.getUsuarioEntity() == null
          && resultado.getCodigobd().intValue() == ConstantesNumericas.DOS ) {
        log.info("[El usuario ya existe en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.ERROR_INSERT,
            ApiErrorCode.CONFLICTO_INTEGRIDAD
        );
      }
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
          "[DataAccessException | Error al insertar el usuario "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al insertar el usuario | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza insertar usuario | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> eliminarUsuarioService(UsuarioEliminarReactivarDto usuario) {
    log.info("[Inicia eliminar usuario | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      // Llamada al Repository
      Integer resultado =
          this.usuarioRepository.eliminarUsuarioRepository(usuario.getIdUsuario(), usuario.getEmail(),
              usuario.getTenantId());
      
      if ( resultado == null || resultado == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Hubo un problema al eliminar el usuario de la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.ERROR_ELIMINAR,
            ApiErrorCode.ERROR_BASE_DATOS
        );
      }
      
      if ( resultado == ConstantesNumericas.DOS ) {
        log.info("[No existe el usuario a eliminar o esta inactivo en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.ERROR_ELIMINAR,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      if ( resultado == ConstantesNumericas.CERO ) {
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesOrdenes.OPERACION_EXITOSA,
            ConstantesNumericas.CERO, resultado
        );
      }
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
          "[DataAccessException | Error al eliminar usuario "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al eliminar usuario | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza eliminar usuario | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> reactivarUsuarioService(UsuarioEliminarReactivarDto usuario) {
    log.info("[Inicia eliminar usuario | Service]");
    
    ServiceResult<Object> serviceResult = null;
    
    try {
      // Llamada al Repository
      UsuarioResponseRepository resultado =
          this.usuarioRepository.reactivarUsuarioRepository(usuario.getEmail(), usuario.getTenantId());
      
      if ( resultado == null || resultado.getCodigobd() == ConstantesNumericas.UNONEGATIVO ) {
        log.info("[Hubo un problema al reactivar el usuario de la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.ERROR_ELIMINAR,
            ApiErrorCode.ERROR_BASE_DATOS
        );
      }
      
      if ( resultado.getCodigobd() == ConstantesNumericas.DOS ) {
        log.info("[No existe el usuario a eliminar o esta inactivo en la BD | Service]");
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultError(
            ConstantesOrdenes.ERROR_ELIMINAR,
            ApiErrorCode.SIN_INFORMACION_EN_BD
        );
      }
      
      if ( resultado.getCodigobd() == ConstantesNumericas.CERO ) {
        LoginUsuarioResponse loginUsuarioResponse =
            this.mapearObjetosUsuario.toDtoLoginUsuarioMapper(resultado.getUsuarioEntity());
        serviceResult = this.mapearRespuestasConsultas.mapearserviceResultRespuestaOk(
            ConstantesOrdenes.OPERACION_EXITOSA,
            ConstantesNumericas.CERO, loginUsuarioResponse
        );
      }
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
          "[DataAccessException | Error al reactivar usuario "
              + "| Service | Mas detalles: {}]", e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_BASE_DATOS
          );
    }
    catch ( Exception e ) {
      log.error(
          "[Exception | Error critico al reactivar usuario | Service | Mas detalles: {}]",
          e.getMessage(), e);
      serviceResult =
          this.mapearRespuestasConsultas.mapearserviceResultError(
              ConstantesOrdenes.ERROR_BD,
              ApiErrorCode.ERROR_INTERNO
          );
    }
    finally {
      log.info("[Finaliza reactivar usuario | Service]");
    }
    
    return serviceResult;
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
    finally {
      log.info("[Finaliza listar usuarios | Service]");
    }
    
    return serviceResult;
  }
  
  @Override
  public ServiceResult<Object> actualizarUsuarioService(UsuarioEntity usuario) {
    return null;
  }
  
}

package com.washtrack.washtrack_api.usuarios.controller;

import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.usuarios.dto.BuscarUsuarioRequest;
import com.washtrack.washtrack_api.usuarios.dto.LoginRequest;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioInsertDto;
import com.washtrack.washtrack_api.usuarios.service.IUsuarioService;
import com.washtrack.washtrack_api.util.exceptions.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@CrossOrigin
@RequestMapping("${base.path}")
@RestController
public class UsuariosController {
  
  private final IUsuarioService usuarioService;
  
  public UsuariosController(IUsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }
  
  /**
   * Login de usuarios | Controller
   *
   * Este apartado, incluso funciona para mi, como duenio de la App, ya que puedo iniciar sesion con mi super usuario y
   * poder consultar usuarios
   *
   * @return
   */
  @GetMapping("/usuarios/login")
  public ResponseEntity<ServiceResult<Object>> loginUsuariosController(
      @Validated @RequestBody LoginRequest loginRequest) {
    
    log.info("[Iniciando login de usuario | Controller]");
    
    ServiceResult<Object> resultado = this.usuarioService.consultarUsuarioLogInService(loginRequest);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      
      return ResponseEntity
          .status(error.getHttpStatus())
          .body(resultado);
    }
    return ResponseEntity.ok(resultado);
    
  }
  
  /**
   * Buscar usuarios | Controller
   *
   * Este recurso es unicamente para AMG, como duenio del sistema, puedo consultar todos los usuarios que existen.
   *
   * @return
   */
  @GetMapping("/users/private/serach/amg")
  public ResponseEntity<ServiceResult<Object>> busquedaUsuariosPrivateController(
      @Validated @RequestBody BuscarUsuarioRequest idUsuario) {
    
    log.info("[Iniciando busqueda privada de usuarios | Controller]");
    
    ServiceResult<Object> resultado = this.usuarioService.buscarUsuarioPorIdService(idUsuario.getIdUsuario());
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      
      return ResponseEntity
          .status(error.getHttpStatus())
          .body(resultado);
    }
    return ResponseEntity.ok(resultado);
    
  }
  
  /**
   * Listar usuarios | Controller
   *
   * Este recurso funciona para el Admin de cada empresa, para ver la lista de usuarios en su sistema. Y tambien para
   * AMG como creador de WashTrack.
   *
   * @return
   */
  @GetMapping("/usuarios/listar")
  public ResponseEntity<ServiceResult<Object>> listarUsuariosController() {
    
    log.info("[Iniciando listar usuarios | Controller]");
    
    ServiceResult<Object> resultado = this.usuarioService.listarUsuariosService();
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      
      return ResponseEntity
          .status(error.getHttpStatus())
          .body(resultado);
    }
    return ResponseEntity.ok(resultado);
    
  }
  
  /**
   * Insertar usuarios | Controller
   *
   * @return
   */
  @PostMapping("/usuarios/insertar")
  public ResponseEntity<ServiceResult<Object>> insertarUsuariosController(
      @Validated @RequestBody UsuarioInsertDto usuario) {
    
    log.info("[Iniciando insertar usuarios | Controller]");
    
    ServiceResult<Object> resultado = this.usuarioService.insertarUsuarioService(usuario);
    
    if ( !resultado.isSuccess() && resultado.getData() instanceof ApiErrorCode ) {
      
      ApiErrorCode error = (ApiErrorCode) resultado.getData();
      
      return ResponseEntity
          .status(error.getHttpStatus())
          .body(resultado);
    }
    return ResponseEntity.ok(resultado);
    
  }
  
}

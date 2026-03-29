package com.washtrack.washtrack_api.usuarios.service;

import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.usuarios.dto.LoginUsuarioRequest;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioEliminarReactivarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioInsertDto;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;

public interface IUsuarioService {
  
  ServiceResult<Object> consultarUsuarioLogInService(LoginUsuarioRequest loginUsuarioRequest);
  
  ServiceResult<Object> buscarUsuarioPorIdService(String idUsuario);
  
  ServiceResult<Object> insertarUsuarioService(UsuarioInsertDto usuarioInsertDto);
  
  ServiceResult<Object> eliminarUsuarioService(UsuarioEliminarReactivarDto usuario);
  
  ServiceResult<Object> reactivarUsuarioService(UsuarioEliminarReactivarDto usuario);
  
  ServiceResult<Object> listarUsuariosService();
  
  ServiceResult<Object> actualizarUsuarioService(UsuarioEntity usuario);
}

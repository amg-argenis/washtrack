package com.washtrack.washtrack_api.usuarios.service;

import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.usuarios.dto.LoginRequest;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioEliminarReactivarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioInsertDto;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;

import java.util.List;

public interface IUsuarioService {
  
  ServiceResult<Object> consultarUsuarioLogInService(LoginRequest loginRequest);
  
  ServiceResult<Object> buscarUsuarioPorIdService(String idUsuario);
  
  ServiceResult<Object> insertarUsuarioService(UsuarioInsertDto usuarioInsertDto);
  
  ServiceResult<Object> eliminarUsuarioService(UsuarioEliminarReactivarDto usuario);
  
  ServiceResult<Object> listarUsuariosService();
  
  ServiceResult<Object> actualizarUsuarioService(UsuarioEntity usuario);
}

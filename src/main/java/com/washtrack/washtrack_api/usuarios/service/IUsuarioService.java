package com.washtrack.washtrack_api.usuarios.service;

import com.washtrack.washtrack_api.orden.response.ServiceResult;
import com.washtrack.washtrack_api.usuarios.dto.LoginRequest;
import com.washtrack.washtrack_api.usuarios.dto.LoginResponse;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;

import java.util.List;

public interface IUsuarioService {
  
  ServiceResult<Object> consultarUsuarioLogInService(LoginRequest loginRequest);
  
  ServiceResult<Object> buscarUsuarioPorIdService(String idUsuario);
  
  ServiceResult<Object> insertarUsuarioService(UsuarioEntity usuario);
  
  ServiceResult<Object> eliminarUsuarioService(UsuarioEntity usuario);
  
  ServiceResult<Object> listarUsuariosService();
  
  ServiceResult<Object> actualizarUsuarioService(UsuarioEntity usuario);
}

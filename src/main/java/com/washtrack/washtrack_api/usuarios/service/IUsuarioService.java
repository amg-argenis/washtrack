package com.washtrack.washtrack_api.usuarios.service;

import com.washtrack.washtrack_api.util.response.ServiceResult;
import com.washtrack.washtrack_api.usuarios.dto.BuscarUsuarioRequest;
import com.washtrack.washtrack_api.usuarios.dto.LoginUsuarioRequest;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioActualizarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioEliminarReactivarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioInsertDto;

public interface IUsuarioService {
  
  ServiceResult<Object> consultarUsuarioLogInService(LoginUsuarioRequest loginUsuarioRequest);
  
  ServiceResult<Object> buscarUsuarioPorIdService(String idUsuario);
  
  ServiceResult<Object> buscarUsuarioPorEmailService(String email, String tenantId);
  
  ServiceResult<Object> insertarUsuarioService(UsuarioInsertDto usuarioInsertDto);
  
  ServiceResult<Object> eliminarUsuarioService(UsuarioEliminarReactivarDto usuario);
  
  ServiceResult<Object> reactivarUsuarioService(UsuarioEliminarReactivarDto usuario);
  
  ServiceResult<Object> listarUsuariosService();
  
  ServiceResult<Object> listarUsuariosPorTenantIdService(String tenantId);
  
  ServiceResult<Object> actualizarUsuarioService(UsuarioActualizarDto usuario);
}

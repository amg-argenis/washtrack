package com.washtrack.washtrack_api.usuarios.repository;

import com.washtrack.washtrack_api.usuarios.dto.UsuarioResponseRepository;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioInsertEntity;

import java.util.List;

public interface IUsuarioRepository {
  
  com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity consultarUsuarioLogInRepository(String email,
      String password);
  
  UsuarioResponseRepository buscarUsuarioPorIdRepository(String idUsuario);
  
  UsuarioResponseRepository buscarUsuarioPorEmailRepository(String email, String tenantId);
  
  UsuarioResponseRepository insertarUsuarioRepository(UsuarioInsertEntity usuarioInsert);
  
  Integer eliminarUsuarioRepository(String idUsuario, String email, String tenantId);
  
  UsuarioResponseRepository reactivarUsuarioRepository(String email, String tenantId);
  
  List<UsuarioEntity> listarUsuariosRepository();
  
  List<UsuarioEntity> listarUsuariosPorTenantIdRepository(String tenantId);
  
  UsuarioResponseRepository actualizarUsuarioRepository(UsuarioInsertEntity usuario);
  
}

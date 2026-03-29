package com.washtrack.washtrack_api.usuarios.repository;

import com.washtrack.washtrack_api.usuarios.dto.UsuarioResponseRepository;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioInsertEntity;

import java.util.List;

public interface IUsuarioRepository {
  
  com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity consultarUsuarioLogInRepository(String email,
      String password);
  
  UsuarioEntity buscarUsuarioPorIdRepository(String idUsuario);
  
  UsuarioResponseRepository insertarUsuarioRepository(UsuarioInsertEntity usuarioInsert);
  
  Integer eliminarUsuarioRepository(UsuarioEntity usuario);
  
  List<UsuarioEntity> listarUsuariosRepository();
  
  Integer actualizarUsuarioRepository(UsuarioEntity usuario);
  
}

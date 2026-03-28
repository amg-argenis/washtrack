package com.washtrack.washtrack_api.usuarios.repository;

import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;

import java.util.List;

public interface IUsuarioRepository {
  
  UsuarioEntity consultarUsuario(String email, String password);
  
  UsuarioEntity buscarUsuarioPorId(String idUsuario);
  
  UsuarioEntity insertarUsuario(UsuarioEntity usuario);
  
  Integer eliminarUsuario(UsuarioEntity usuario);
  
  List<UsuarioEntity> listarUsuarios();
  
  Integer actualizarUsuario(UsuarioEntity usuario);
  
}

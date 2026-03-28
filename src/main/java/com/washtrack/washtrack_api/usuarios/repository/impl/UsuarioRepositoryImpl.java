package com.washtrack.washtrack_api.usuarios.repository.impl;

import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import com.washtrack.washtrack_api.usuarios.repository.IUsuarioRepository;

import java.util.List;

public class UsuarioRepositoryImpl implements IUsuarioRepository {
  @Override
  public UsuarioEntity consultarUsuario(String email, String password) {
    return null;
  }
  
  @Override
  public UsuarioEntity buscarUsuarioPorId(String idUsuario) {
    return null;
  }
  
  @Override
  public UsuarioEntity insertarUsuario(UsuarioEntity usuario) {
    return null;
  }
  
  @Override
  public Integer eliminarUsuario(UsuarioEntity usuario) {
    return 0;
  }
  
  @Override
  public List<UsuarioEntity> listarUsuarios() {
    return List.of();
  }
  
  @Override
  public Integer actualizarUsuario(UsuarioEntity usuario) {
    return 0;
  }
}

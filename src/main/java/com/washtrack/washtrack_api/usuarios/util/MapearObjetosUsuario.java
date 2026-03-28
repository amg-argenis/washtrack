package com.washtrack.washtrack_api.usuarios.util;

import com.washtrack.washtrack_api.usuarios.dto.LoginRequest;
import com.washtrack.washtrack_api.usuarios.dto.LoginResponse;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioInsertEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapearObjetosUsuario {
  
  // Metodo para mapear insert de usuarios
  public Map<String, Object> insertarUsuarioMapper(UsuarioInsertEntity usuarioInsertEntity) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_password", usuarioInsertEntity.getPassword());
    params.put("pa_tenantId", usuarioInsertEntity.getTenantId());
    params.put("pa_nombre", usuarioInsertEntity.getNombre());
    params.put("pa_email", usuarioInsertEntity.getEmail());
    params.put("pa_rol", usuarioInsertEntity.getRol());
    
    return params;
  }
  
  // Metodo para mapear actualizar usuarios
  public Map<String, Object> actualizarUsuarioMapper(UsuarioInsertEntity usuarioInsertEntity) {
    Map<String, Object> params = new HashMap<>();
    // Criterio de busqueda en BD
    params.put("pa_idusuario", usuarioInsertEntity.getIdUsuario());
    params.put("pa_password", usuarioInsertEntity.getPassword());
    params.put("pa_nombre", usuarioInsertEntity.getNombre());
    params.put("pa_email", usuarioInsertEntity.getEmail());
    params.put("pa_rol", usuarioInsertEntity.getRol());
    // Criterio de busqueda en BD
    params.put("pa_tenantId", usuarioInsertEntity.getTenantId());
    
    return params;
  }
  
  public LoginResponse toDtoLoginUsuarioMapper(UsuarioEntity usuarioEntity) {
    return LoginResponse.builder()
        .idUsuario(usuarioEntity.getIdUsuario())
        .tenantId(usuarioEntity.getTenantId())
        .nombre(usuarioEntity.getNombre())
        .email(usuarioEntity.getEmail())
        .rol(usuarioEntity.getRol())
        .activo(usuarioEntity.isActivo())
        .build();
  }
  
}

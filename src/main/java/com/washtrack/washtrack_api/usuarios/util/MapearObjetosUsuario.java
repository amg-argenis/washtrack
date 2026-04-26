package com.washtrack.washtrack_api.usuarios.util;

import com.washtrack.washtrack_api.usuarios.dto.LoginUsuarioResponse;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioActualizarDto;
import com.washtrack.washtrack_api.usuarios.dto.UsuarioInsertDto;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import com.washtrack.washtrack_api.usuarios.entity.UsuarioInsertEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapearObjetosUsuario {
  
  // PARAMETERS
  
  public Map<String, Object> insertarUsuarioParams(UsuarioInsertEntity usuarioInsertEntity) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_idusuario", usuarioInsertEntity.getIdUsuario());
    params.put("pa_tenantid", usuarioInsertEntity.getTenantId());
    params.put("pa_nombre", usuarioInsertEntity.getNombre());
    params.put("pa_email", usuarioInsertEntity.getEmail());
    params.put("pa_password", usuarioInsertEntity.getPassword());
    params.put("pa_rol", usuarioInsertEntity.getRol());
    
    return params;
  }
  
  // Metodo para mapear actualizar usuarios
  public Map<String, Object> actualizarUsuarioParameters(UsuarioInsertEntity usuarioInsertEntity) {
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
  
  // MAPPERS
  
  public LoginUsuarioResponse toDtoLoginUsuarioMapper(UsuarioEntity usuarioEntity) {
    return LoginUsuarioResponse.builder()
        .idUsuario(usuarioEntity.getIdUsuario())
        .tenantId(usuarioEntity.getTenantId())
        .nombre(usuarioEntity.getNombre())
        .email(usuarioEntity.getEmail())
        .rol(usuarioEntity.getRol())
        .activo(usuarioEntity.isActivo())
        .nombreTenant(usuarioEntity.getNombreTenant())
        .token(usuarioEntity.getToken())
        .build();
  }
  
  public UsuarioInsertEntity insertarUsuarioMapper(UsuarioInsertDto usuarioActualizarDto) {
    return UsuarioInsertEntity.builder()
        .tenantId(usuarioActualizarDto.getTenantId())
        .nombre(usuarioActualizarDto.getNombre())
        .email(usuarioActualizarDto.getEmail())
        .password(usuarioActualizarDto.getPassword())
        .rol(usuarioActualizarDto.getRol())
        .build();
  }
  
  public UsuarioInsertEntity actualizarUsuarioMapper(UsuarioActualizarDto usuarioActualizarDto) {
    return UsuarioInsertEntity.builder()
        .idUsuario(usuarioActualizarDto.getIdUsuario())
        .tenantId(usuarioActualizarDto.getTenantId())
        .nombre(usuarioActualizarDto.getNombre())
        .email(usuarioActualizarDto.getEmail())
        .password(usuarioActualizarDto.getPassword())
        .rol(usuarioActualizarDto.getRol())
        .build();
  }
  
}

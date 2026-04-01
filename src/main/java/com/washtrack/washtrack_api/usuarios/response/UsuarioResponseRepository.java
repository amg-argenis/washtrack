package com.washtrack.washtrack_api.usuarios.response;

import com.washtrack.washtrack_api.usuarios.entity.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioResponseRepository {
  private Integer codigobd;
  private UsuarioEntity usuarioEntity;
}

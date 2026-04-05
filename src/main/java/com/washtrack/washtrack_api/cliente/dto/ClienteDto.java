package com.washtrack.washtrack_api.cliente.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
  private String idCliente;
  private String tenantId;
  private String nombre;
  private String contacto;
  private String telefono;
  private String email;
  private Boolean creditoHabilitado;
  private Double limiteCredito;
  private Boolean activo;
}

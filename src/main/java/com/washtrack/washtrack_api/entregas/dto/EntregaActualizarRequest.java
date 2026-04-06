package com.washtrack.washtrack_api.entregas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntregaActualizarRequest {
  
  @NotBlank(message = "Debe proporcionar el Id de la entrega, favor de verificar")
  @Length(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id entrega")
  private String idEntrega;
  
  private String tenantId;
  
  @NotBlank(message = "Campo requerido para la operacion, favor de verificar")
  @Size(min = 10, max = 36, message = "El numero de caracteres es invalido al permitido para el Id orden")
  private String ordenId;
  
  @NotNull(message = "Campo fecha requerido para actualizar, favor de verificar")
  private LocalDate fechaEntrega;
  
  @NotNull(message = "Campo totalEntregado es requerido, favor de verificar")
  @Min(value = 1, message = "El total entregado debe ser mayor a 0, favor de verificar")
  private Integer totalEntregado;
  
  @NotNull(message = "Campo conformidad de cliente requerido para la operacion, favor de verificar")
  private Boolean conformidadCliente;
  
  private String observaciones;
  
  @NotBlank(message = "Campo estado requerido para la operacion, favor de verificar")
  @Pattern(
      regexp = "^(ENTREGADO|ELIMINADO)$",
      message = "El estado debe ser ENTREGADO o ELIMINADO"
  )
  private String estado;
  
}

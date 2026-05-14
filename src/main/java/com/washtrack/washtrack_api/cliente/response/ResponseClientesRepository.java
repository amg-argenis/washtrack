package com.washtrack.washtrack_api.cliente.response;

import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseClientesRepository {
  private Integer codigobd;
  private ClientesEntity clientes;
  private List<ClientesEntity> entityList;
}

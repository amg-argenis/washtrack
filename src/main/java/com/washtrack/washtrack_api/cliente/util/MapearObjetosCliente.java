package com.washtrack.washtrack_api.cliente.util;

import com.washtrack.washtrack_api.cliente.dto.ClienteDto;
import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import org.springframework.stereotype.Component;

@Component
public class MapearObjetosCliente {
  
  /**
   * Mapeo de objetos para envio y recepcion al Front
   *
   * @param clientesEntity
   * @return
   */
  public ClienteDto mapearClienteToDto(ClientesEntity clientesEntity) {
    
    return ClienteDto.builder()
        .idCliente(clientesEntity.getIdCliente())
        .tenantId(clientesEntity.getTenantId())
        .nombre(clientesEntity.getNombre())
        .contacto(clientesEntity.getContacto())
        .telefono(clientesEntity.getTelefono())
        .email(clientesEntity.getEmail())
        .creditoHabilitado(clientesEntity.isCreditoHabilitado())
        .limiteCredito(clientesEntity.getLimiteCredito())
        .activo(clientesEntity.getActivo())
        .build();
  }
  
  /**
   * Mapeo de objetos para uso interno en el BK
   *
   * @param clienteDto
   * @return
   */
  public ClientesEntity mapearClienteDtoToEntity(ClienteDto clienteDto) {
    
    return ClientesEntity.builder()
        .idCliente(clienteDto.getIdCliente())
        .tenantId(clienteDto.getTenantId())
        .nombre(clienteDto.getNombre())
        .contacto(clienteDto.getContacto())
        .telefono(clienteDto.getTelefono())
        .email(clienteDto.getEmail())
        .creditoHabilitado(clienteDto.isCreditoHabilitado())
        .limiteCredito(clienteDto.getLimiteCredito())
        .activo(clienteDto.getActivo())
        .build();
  }
  
}
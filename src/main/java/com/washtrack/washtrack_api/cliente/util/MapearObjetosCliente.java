package com.washtrack.washtrack_api.cliente.util;

import com.washtrack.washtrack_api.cliente.dto.ClienteDto;
import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
        .activo(clientesEntity.isActivo())
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
        .activo(clienteDto.isActivo())
        .build();
  }
  
  public Map<String, Object> parametrizarObjetoClienteEntity(ClientesEntity clientesEntity) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", "a051a168-fa2a-11f0-aab7-e66133dbb0de"); // Temporal ******
    params.put("pa_idcliente", clientesEntity.getIdCliente());
    params.put("pa_nombre", clientesEntity.getNombre());
    params.put("pa_contacto", clientesEntity.getContacto());
    params.put("pa_telefono", clientesEntity.getTelefono());
    params.put("pa_email", clientesEntity.getEmail());
    params.put("pa_creditohabilitado", clientesEntity.isCreditoHabilitado());
    params.put("pa_limitecredito", clientesEntity.getLimiteCredito());
    
    return params;
  }
  
}
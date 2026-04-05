package com.washtrack.washtrack_api.cliente.util;

import com.washtrack.washtrack_api.cliente.dto.ActualizarClienteDto;
import com.washtrack.washtrack_api.cliente.dto.ClienteDto;
import com.washtrack.washtrack_api.cliente.dto.InsertarClienteDto;
import com.washtrack.washtrack_api.cliente.entity.ClientesEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapearObjetosCliente {
  
  // MAPERS
  
  public ClienteDto mapearClienteToDto(ClientesEntity clientesEntity) {
    
    return ClienteDto.builder()
        .idCliente(clientesEntity.getIdCliente())
        .tenantId(clientesEntity.getTenantId())
        .nombre(clientesEntity.getNombre())
        .contacto(clientesEntity.getContacto())
        .telefono(clientesEntity.getTelefono())
        .email(clientesEntity.getEmail())
        .creditoHabilitado(clientesEntity.getCreditoHabilitado())
        .limiteCredito(clientesEntity.getLimiteCredito())
        .activo(clientesEntity.getActivo())
        .build();
  }
  
  public ClientesEntity mapearClienteDtoToEntity(InsertarClienteDto actualizarClienteDto) {
    
    return ClientesEntity.builder()
        .idCliente(actualizarClienteDto.getIdCliente())
        .tenantId(actualizarClienteDto.getTenantId())
        .nombre(actualizarClienteDto.getNombre())
        .contacto(actualizarClienteDto.getContacto())
        .telefono(actualizarClienteDto.getTelefono())
        .email(actualizarClienteDto.getEmail())
        .creditoHabilitado(actualizarClienteDto.getCreditoHabilitado())
        .limiteCredito(actualizarClienteDto.getLimiteCredito())
        .build();
  }
  
  public ClientesEntity mapearClienteDtoToEntity(ActualizarClienteDto actualizarClienteDto) {
    
    return ClientesEntity.builder()
        .idCliente(actualizarClienteDto.getIdCliente())
        .tenantId(actualizarClienteDto.getTenantId())
        .nombre(actualizarClienteDto.getNombre())
        .contacto(actualizarClienteDto.getContacto())
        .telefono(actualizarClienteDto.getTelefono())
        .email(actualizarClienteDto.getEmail())
        .creditoHabilitado(actualizarClienteDto.getCreditoHabilitado())
        .limiteCredito(actualizarClienteDto.getLimiteCredito())
        .activo(actualizarClienteDto.getActivo())
        .build();
  }
  
  // PAREMETER SETTER
  
  public Map<String, Object> parametrizarObjetoClienteEntity(ClientesEntity clientesEntity) {
    Map<String, Object> params = new HashMap<>();
    params.put("pa_tenantid", clientesEntity.getTenantId());
    params.put("pa_idcliente", clientesEntity.getIdCliente());
    params.put("pa_nombre", clientesEntity.getNombre());
    params.put("pa_contacto", clientesEntity.getContacto());
    params.put("pa_telefono", clientesEntity.getTelefono());
    params.put("pa_email", clientesEntity.getEmail());
    params.put("pa_creditohabilitado", clientesEntity.getCreditoHabilitado());
    params.put("pa_limitecredito", clientesEntity.getLimiteCredito());
    
    return params;
  }
  
}
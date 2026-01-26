package com.washtrack.washtrack_api.orden.respository.impl;

import com.washtrack.washtrack_api.orden.entity.OrdenesEntity;
import com.washtrack.washtrack_api.orden.respository.IOrdenesRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdenesRepositoryImpl implements IOrdenesRepository {
  @Override
  public List<OrdenesEntity> listarOrdenesRepository() {
    return List.of();
  }
}

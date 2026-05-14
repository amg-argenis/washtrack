package com.washtrack.washtrack_api.entregas.response;

import com.washtrack.washtrack_api.entregas.entity.EntregasEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntregasResponseRepository {
  private Integer codigobd;
  private EntregasEntity entregasEntity;
  private List<EntregasEntity> entityList;
}

package com.tx.travel.mapper;

import com.tx.travel.model.CostCenter;
import com.tx.travel.payload.request.CostCenterRequest;
import com.tx.travel.payload.response.CostCenterResponse;
import org.springframework.stereotype.Component;

@Component
public class CostCenterMapperImplementation {

  public CostCenter CostCenterRequestToCostCenter(final CostCenterRequest costCenterRequest) {
    return CostCenterRequestToCostCenter(costCenterRequest, null);
  }

  public CostCenter CostCenterRequestToCostCenter(
      final CostCenterRequest costCenterRequest, final Long id) {
    return CostCenter.builder()
        .id(id)
        .code(costCenterRequest.getCode())
        .name(costCenterRequest.getName())
        .build();
  }

  public CostCenterResponse CostCenterToCostCenterResponse(final CostCenter costCenter) {
    return CostCenterResponse.builder()
        .id(costCenter.getId())
        .code(costCenter.getCode())
        .name(costCenter.getName())
        .build();
  }
}

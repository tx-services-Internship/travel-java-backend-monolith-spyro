package com.tx.travel.mapper;

import com.tx.travel.model.CostCenter;
import com.tx.travel.payload.request.CostCenterRequest;
import com.tx.travel.payload.response.CostCentreResponse;
import org.springframework.stereotype.Component;


@Component
public class CostCenterMapperImplementation implements CostCenterMapperInterface{

  @Override
  public CostCenter CostCenterRequestToCostCenter(final CostCenterRequest costCenterRequest) {
    return CostCenterRequestToCostCenter(costCenterRequest, null);
  }

  public CostCenter CostCenterRequestToCostCenter(final CostCenterRequest costCenterRequest, final Long id){
    return CostCenter.builder()
        .id(id)
        .code(costCenterRequest.getCode())
        .name(costCenterRequest.getName())
        .build();
  }

  @Override
  public CostCentreResponse CostCenterToCostCenterResponse(final CostCenter costCenter) {
    return CostCentreResponse.builder()
        .id(costCenter.getId())
        .code(costCenter.getCode())
        .name(costCenter.getName())
        .build();
  }


}

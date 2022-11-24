package com.tx.travel.mapper;

import com.tx.travel.model.CostCenter;
import com.tx.travel.payload.request.CostCenterRequest;
import com.tx.travel.payload.response.CostCentreResponse;

public interface CostCenterMapperInterface {

  CostCenter CostCenterRequestToCostCenter(final CostCenterRequest costCenterRequest);

  CostCenter CostCenterRequestToCostCenter(final CostCenterRequest costCenterRequest, final Long id);

  CostCentreResponse CostCenterToCostCenterResponse(final CostCenter costCenter);


}

package com.tx.travel.security.services;

import com.tx.travel.payload.request.CostCenterRequest;
import com.tx.travel.payload.response.CostCentreResponse;
import com.tx.travel.service.exception.CostCenterCodeAlreadyExists;
import com.tx.travel.service.exception.CostCenterNotPresent;
import java.util.List;
import org.springframework.stereotype.Service;


public interface CostCenterService {
  public List<CostCentreResponse> fetchCostCenters();
  public CostCentreResponse fetchCostCenterById(Long id);
  public CostCentreResponse saveCostCenter(CostCenterRequest costCenter);
  public void deleteCostCenterById(Long id);
  public CostCentreResponse updateCostCenterById(Long id, CostCenterRequest costCenter);

}

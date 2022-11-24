package com.tx.travel.security.services;

import com.tx.travel.model.CostCenter;
import com.tx.travel.payload.request.CostCenterRequest;
import com.tx.travel.payload.response.CostCentreResponse;
import com.tx.travel.service.exception.CostCenterCodeAlreadyExists;
import com.tx.travel.service.exception.CostCenterNotPresent;
import java.util.List;
import org.springframework.stereotype.Service;


public interface CostCenterService {
  public List<CostCenter> fetchCostCenters();
  public CostCenter fetchCostCenterById(Long id);
  public CostCenter saveCostCenter(CostCenter costCenter);
  public void deleteCostCenterById(Long id);
  public CostCenter updateCostCenterById(Long id, CostCenter costCenter);

}

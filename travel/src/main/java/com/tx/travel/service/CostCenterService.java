package com.tx.travel.service;

import com.tx.travel.model.CostCenter;
import java.util.List;

public interface CostCenterService {
  List<CostCenter> fetchCostCenters();

  CostCenter fetchCostCenterById(Long id);

  CostCenter saveCostCenter(CostCenter costCenter);

  void deleteCostCenterById(Long id);

  CostCenter updateCostCenterById(Long id, CostCenter costCenter);
}

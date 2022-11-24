package com.tx.travel.security.services;

import com.tx.travel.model.CostCenter;
import com.tx.travel.repository.CostCenterRepository;
import com.tx.travel.service.exception.CostCenterCodeAlreadyExists;
import com.tx.travel.service.exception.CostCenterNotPresent;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CostCenterServiceImplementation implements CostCenterService{

  final CostCenterRepository costCenterRepository;

  public CostCenterServiceImplementation(final CostCenterRepository costCenterRepository){
    this.costCenterRepository = costCenterRepository;
  }

  @Override
  public List<CostCenter> fetchCostCenters() {

    return costCenterRepository.findAll();

  }

  @Override
  public CostCenter fetchCostCenterById(Long id) throws CostCenterNotPresent{

    Optional<CostCenter> fetchedCostCenter = costCenterRepository.findById(id);
    if(fetchedCostCenter.isEmpty()) throw new CostCenterNotPresent(id);

    return fetchedCostCenter.get();

  }

  @Override
  public CostCenter saveCostCenter(@Valid CostCenter costCenter)
      throws CostCenterCodeAlreadyExists{

    if(costCenterRepository.findByCode(costCenter.getCode()).isPresent())
      throw new CostCenterCodeAlreadyExists(costCenter.getCode());

    return costCenterRepository.save(costCenter);
  }

  @Override
  public void deleteCostCenterById(Long id) throws CostCenterNotPresent {
    if(costCenterRepository.findById(id).isEmpty()) throw new CostCenterNotPresent(id);
    costCenterRepository.deleteById(id);
  }

  @Override
  public CostCenter updateCostCenterById(Long id, @Valid final CostCenter costCenter)
      throws CostCenterNotPresent, CostCenterCodeAlreadyExists{

    if (costCenterRepository.findById(id).isEmpty()) throw new CostCenterNotPresent(id);
    else if (costCenterRepository.findByCode(costCenter.getCode()).isPresent() &&
    !costCenterRepository.findById(id).get().getCode().equals(costCenter.getCode()))
      throw new CostCenterCodeAlreadyExists(costCenter.getCode());


    return costCenterRepository.save(costCenter);

  }

}

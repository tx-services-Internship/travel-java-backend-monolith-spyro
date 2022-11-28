package com.tx.travel.service;

import com.tx.travel.model.CostCenter;
import com.tx.travel.repository.CostCenterRepository;
import com.tx.travel.service.exception.CostCenterCodeAlreadyExistsException;
import com.tx.travel.service.exception.CostCenterNotPresentException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CostCenterServiceImplementation implements CostCenterService {

  private final CostCenterRepository costCenterRepository;

  public CostCenterServiceImplementation(final CostCenterRepository costCenterRepository) {
    this.costCenterRepository = costCenterRepository;
  }

  @Override
  public List<CostCenter> fetchCostCenters() {

    return costCenterRepository.findAll();
  }

  @Override
  public CostCenter fetchCostCenterById(Long id) throws CostCenterNotPresentException {

    Optional<CostCenter> fetchedCostCenter = costCenterRepository.findById(id);
    if (fetchedCostCenter.isEmpty()) throw new CostCenterNotPresentException(id);

    return fetchedCostCenter.get();
  }

  @Override
  public CostCenter saveCostCenter(@Valid CostCenter costCenter)
      throws CostCenterCodeAlreadyExistsException {

    if (costCenterRepository.findByCode(costCenter.getCode()).isPresent())
      throw new CostCenterCodeAlreadyExistsException(costCenter.getCode());

    return costCenterRepository.save(costCenter);
  }

  @Override
  public void deleteCostCenterById(Long id) throws CostCenterNotPresentException {
    if (costCenterRepository.findById(id).isEmpty()) throw new CostCenterNotPresentException(id);
    costCenterRepository.deleteById(id);
  }

  @Override
  public CostCenter updateCostCenterById(Long id, @Valid final CostCenter costCenter)
      throws CostCenterNotPresentException, CostCenterCodeAlreadyExistsException {

    if (costCenterRepository.findById(id).isEmpty()) throw new CostCenterNotPresentException(id);
    else if (costCenterRepository.findByCode(costCenter.getCode()).isPresent()
        && !costCenterRepository.findById(id).get().getCode().equals(costCenter.getCode()))
      throw new CostCenterCodeAlreadyExistsException(costCenter.getCode());

    return costCenterRepository.save(costCenter);
  }
}

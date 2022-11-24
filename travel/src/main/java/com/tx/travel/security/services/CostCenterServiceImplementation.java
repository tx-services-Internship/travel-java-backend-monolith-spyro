package com.tx.travel.security.services;

import com.tx.travel.model.CostCenter;
import com.tx.travel.payload.request.CostCenterRequest;
import com.tx.travel.payload.response.CostCentreResponse;
import com.tx.travel.repository.CostCenterRepository;
import com.tx.travel.service.exception.CostCenterCodeAlreadyExists;
import com.tx.travel.service.exception.CostCenterNotPresent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;


@Service
public class CostCenterServiceImplementation implements CostCenterService{

  final CostCenterRepository costCenterRepository;

  public CostCenterServiceImplementation(final CostCenterRepository costCenterRepository){
    this.costCenterRepository = costCenterRepository;
  }


  @Override
  public List<CostCentreResponse> fetchCostCenters() {
    List<CostCenter> fetchedList = costCenterRepository.findAll();
    List<CostCentreResponse> returnList = new ArrayList<>();
    for(CostCenter costCenter: fetchedList){
      CostCentreResponse costCenterResponse = CostCentreResponse.builder()
          .id(costCenter.getId())
          .code(costCenter.getCode())
          .name(costCenter.getName())
          .build();
      returnList.add(costCenterResponse);
    }
    return returnList;
  }

  @Override
  public CostCentreResponse fetchCostCenterById(Long id) throws CostCenterNotPresent{
    Optional<CostCenter> fetchedCostCenter = costCenterRepository.findById(id);
    if(fetchedCostCenter.isEmpty()) throw new CostCenterNotPresent(id);
    CostCenter costCenter = fetchedCostCenter.get();
    return CostCentreResponse.builder()
        .id(costCenter.getId())
        .code(costCenter.getCode())
        .name(costCenter.getName())
        .build();
  }

  @Override
  public CostCentreResponse saveCostCenter(@Valid CostCenterRequest costCenter) {
    CostCenter addedCostCenter = costCenterRepository.save(
        CostCenter.builder()
            .name(costCenter.getName())
            .code(costCenter.getCode())
            .build()
    );
    return CostCentreResponse.builder()
        .id(addedCostCenter.getId())
        .code(addedCostCenter.getCode())
        .name(addedCostCenter.getName())
        .build();
  }

  @Override
  public void deleteCostCenterById(Long id) {

    costCenterRepository.deleteById(id);
  }

  @Override
  public CostCentreResponse updateCostCenterById(Long id, @Valid CostCenterRequest costCenter) {

    CostCenter updatedCostCenter = costCenterRepository.save(
        CostCenter.builder()
            .id(id)
            .name(costCenter.getName())
            .code(costCenter.getCode())
            .build()
    );

    return CostCentreResponse.builder()
        .id(updatedCostCenter.getId())
        .code(updatedCostCenter.getCode())
        .name(updatedCostCenter.getName())
        .build();

  }

  public void findByCode(@NotNull final String code) throws CostCenterCodeAlreadyExists{
    final Optional<CostCenter> costCenter = costCenterRepository.findByCode(code);
    if(costCenter.isPresent()) throw new CostCenterCodeAlreadyExists(code);
  }

  public void findById(final Long id) throws CostCenterNotPresent{
    final Optional<CostCenter> costCenter = costCenterRepository.findById(id);
    if(costCenter.isEmpty()) throw new CostCenterNotPresent(id);
  }


}

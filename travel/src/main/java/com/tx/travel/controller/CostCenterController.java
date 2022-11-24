package com.tx.travel.controller;


import com.tx.travel.mapper.CostCenterMapperImplementation;
import com.tx.travel.model.CostCenter;
import com.tx.travel.payload.request.CostCenterRequest;
import com.tx.travel.payload.response.CostCentreResponse;
import com.tx.travel.security.services.CostCenterService;
import com.tx.travel.security.services.CostCenterServiceImplementation;
import com.tx.travel.service.exception.CostCenterCodeAlreadyExists;
import com.tx.travel.service.exception.CostCenterNotPresent;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cost-centers")
public class CostCenterController {

  final CostCenterService costCenterService;
  final CostCenterMapperImplementation costCenterMapper;

  public CostCenterController(final CostCenterServiceImplementation costCenterService
      , final CostCenterMapperImplementation costCenterMapper){
    this.costCenterService = costCenterService;
    this.costCenterMapper = costCenterMapper;
  }


  @GetMapping()
  public ResponseEntity<List<CostCentreResponse>> fetchCostCenters(){

    List<CostCenter> fetchedList = costCenterService.fetchCostCenters();
    List<CostCentreResponse> responseList = new ArrayList<>();
    for(CostCenter costCenter: fetchedList){
      responseList.add(costCenterMapper.CostCenterToCostCenterResponse(costCenter));
    }

    return ResponseEntity.ok().body(responseList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CostCentreResponse> fetchCostCenterById(@PathVariable("id") Long id){

    try{

      CostCenter fetchedCostCenter = costCenterService.fetchCostCenterById(id);
      return ResponseEntity.ok().body(costCenterMapper.CostCenterToCostCenterResponse(fetchedCostCenter));

    } catch (CostCenterNotPresent e){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

  }

  @PostMapping()
  public ResponseEntity<CostCentreResponse> saveCostCenter(@Valid @RequestBody CostCenterRequest costCenterRequest){

    try{
      CostCenter savedCostCenter = costCenterService.saveCostCenter(
          costCenterMapper.CostCenterRequestToCostCenter(costCenterRequest)
      );
      return ResponseEntity.ok().body(costCenterMapper.CostCenterToCostCenterResponse(savedCostCenter));

    } catch (CostCenterCodeAlreadyExists e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
    }

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCostCenterById(@PathVariable("id") Long id){

    try{

    costCenterService.deleteCostCenterById(id);

    } catch (CostCenterNotPresent e){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<CostCentreResponse> updateCostCenterById(@PathVariable("id") Long id,
      @Valid @RequestBody CostCenterRequest costCenterRequest){
    try{
      CostCenter costCenterToUpdate = costCenterMapper.CostCenterRequestToCostCenter(costCenterRequest, id);
      CostCenter updatedCostCenter = costCenterService.updateCostCenterById(id, costCenterToUpdate);
    return ResponseEntity.ok().body(costCenterMapper.CostCenterToCostCenterResponse(updatedCostCenter));

    } catch (CostCenterCodeAlreadyExists e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
    } catch (CostCenterNotPresent e){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

  }






}

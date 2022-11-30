package com.tx.travel.controller;

import com.tx.travel.mapper.CostCenterMapper;
import com.tx.travel.model.CostCenter;
import com.tx.travel.payload.request.CostCenterRequest;
import com.tx.travel.payload.response.CostCenterResponse;
import com.tx.travel.service.CostCenterService;
import com.tx.travel.service.exception.CostCenterCodeAlreadyExistsException;
import com.tx.travel.service.exception.CostCenterNotPresentException;
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
@RequestMapping("${apiPrefix}/cost-centers")
public class CostCenterController {

  private final CostCenterService costCenterService;
  private final CostCenterMapper costCenterMapper;

  public CostCenterController(
      final CostCenterService costCenterService, final CostCenterMapper costCenterMapper) {
    this.costCenterService = costCenterService;
    this.costCenterMapper = costCenterMapper;
  }

  @GetMapping()
  public ResponseEntity<List<CostCenterResponse>> fetchCostCenters() {

    List<CostCenter> fetchedList = costCenterService.fetchCostCenters();
    List<CostCenterResponse> responseList = new ArrayList<>();
    for (CostCenter costCenter : fetchedList) {
      responseList.add(costCenterMapper.CostCenterToCostCenterResponse(costCenter));
    }

    return ResponseEntity.ok().body(responseList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CostCenterResponse> fetchCostCenterById(@PathVariable("id") Long id) {

    try {
      CostCenter fetchedCostCenter = costCenterService.fetchCostCenterById(id);
      return ResponseEntity.ok()
          .body(costCenterMapper.CostCenterToCostCenterResponse(fetchedCostCenter));

    } catch (CostCenterNotPresentException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }
  }

  @PostMapping()
  public ResponseEntity<CostCenterResponse> createCostCenter(
      @Valid @RequestBody CostCenterRequest costCenterRequest) {

    try {
      CostCenter savedCostCenter =
          costCenterService.saveCostCenter(
              costCenterMapper.CostCenterRequestToCostCenter(costCenterRequest));
      return ResponseEntity.ok()
          .body(costCenterMapper.CostCenterToCostCenterResponse(savedCostCenter));

    } catch (CostCenterCodeAlreadyExistsException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteCostCenterById(@PathVariable("id") Long id) {

    try {
      costCenterService.deleteCostCenterById(id);
    } catch (CostCenterNotPresentException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<CostCenterResponse> updateCostCenterById(
      @PathVariable("id") Long id, @Valid @RequestBody CostCenterRequest costCenterRequest) {
    try {
      CostCenter costCenterToUpdate =
          costCenterMapper.CostCenterRequestToCostCenter(costCenterRequest, id);
      CostCenter updatedCostCenter = costCenterService.updateCostCenterById(id, costCenterToUpdate);
      return ResponseEntity.ok()
          .body(costCenterMapper.CostCenterToCostCenterResponse(updatedCostCenter));

    } catch (CostCenterCodeAlreadyExistsException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
    } catch (CostCenterNotPresentException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }
  }
}

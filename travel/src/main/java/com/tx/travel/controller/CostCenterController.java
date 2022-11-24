package com.tx.travel.controller;


import com.tx.travel.model.CostCenter;
import com.tx.travel.payload.request.CostCenterRequest;
import com.tx.travel.payload.response.CostCentreResponse;
import com.tx.travel.security.services.CostCenterService;
import com.tx.travel.service.exception.CostCenterCodeAlreadyExists;
import com.tx.travel.service.exception.CostCenterNotPresent;
import com.tx.travel.service.exception.EmailAlreadyExistsException;
import com.tx.travel.service.exception.UsernameAlreadyExistsException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cost-centers")
public class CostCenterController {

  final CostCenterService costCenterService;

  public CostCenterController(final CostCenterService costCenterService){
    this.costCenterService = costCenterService;
  }


  @GetMapping()
  public ResponseEntity<List<CostCentreResponse>> fetchCostCenters(){
    return ResponseEntity.ok().body(costCenterService.fetchCostCenters());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CostCentreResponse> fetchCostCenterById(@PathVariable("id") Long id){

    try{

    return ResponseEntity.ok().body(costCenterService.fetchCostCenterById(id));

    } catch (CostCenterNotPresent e){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

  }

  @PostMapping()
  public ResponseEntity<CostCentreResponse> saveCostCenter(@Valid @RequestBody CostCenterRequest costCenter){

    try{

      return ResponseEntity.ok().body(costCenterService.saveCostCenter(costCenter));

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
      @Valid @RequestBody CostCenterRequest costCenter){
    try{

    return ResponseEntity.ok().body(costCenterService.updateCostCenterById(id, costCenter));

    } catch (CostCenterCodeAlreadyExists e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
    } catch (CostCenterNotPresent e){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

  }






}

package com.tx.travel.controller;


import com.tx.travel.model.CostCenter;
import com.tx.travel.payload.request.CostCenterRequest;
import com.tx.travel.payload.response.CostCentreResponse;
import java.util.List;
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

@RestController
@RequestMapping("/api/cost-centers")
public class CostCenterController {

  @GetMapping()
  public ResponseEntity<List<CostCentreResponse>> fetchCostCenters(){
    return ResponseEntity.ok().body(null);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CostCentreResponse> fetchCostCenterById(@PathVariable("id") Long id){
    return ResponseEntity.ok().body(null);
  }

  @PostMapping()
  public ResponseEntity<CostCentreResponse> saveCostCenter(@RequestBody CostCenterRequest costCenter){
    return ResponseEntity.ok().body(null);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCostCenterById(@PathVariable("id") Long id){
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<CostCentreResponse> updateCostCenterById(@PathVariable("id") int id,
      @RequestBody CostCenterRequest costCenter){
    return ResponseEntity.ok().body(null);
  }






}

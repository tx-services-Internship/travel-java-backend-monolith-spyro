package com.tx.travel.controller;

import com.tx.travel.mapper.DailyAllowanceMapper;
import com.tx.travel.model.DailyAllowance;
import com.tx.travel.payload.request.DailyAllowanceRequest;
import com.tx.travel.payload.response.DailyAllowanceResponse;
import com.tx.travel.service.DailyAllowanceService;
import com.tx.travel.service.exception.DailyAllowanceNotFoundException;
import com.tx.travel.service.exception.RegionAlreadyExistsException;
import com.tx.travel.service.exception.RegionNullPointerException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${apiPrefix}/daily-allowances")
public class DailyAllowanceController {

  final DailyAllowanceService dailyAllowanceService;

  private final DailyAllowanceMapper dailyAllowanceMapper;

  public DailyAllowanceController(
      final DailyAllowanceService dailyAllowanceService,
      final DailyAllowanceMapper dailyAllowanceMapper) {
    this.dailyAllowanceMapper = dailyAllowanceMapper;
    this.dailyAllowanceService = dailyAllowanceService;
  }

  private void validateRegion(String region) {
    if (region == null) throw new RegionNullPointerException();
  }

  @GetMapping
  public ResponseEntity<List<DailyAllowanceResponse>> getDailyAllowances() {

    List<DailyAllowance> dailyAllowances = dailyAllowanceService.getAllDailyAllowances();

    List<DailyAllowanceResponse> dailyAllowanceResponses =
        dailyAllowanceMapper.mapDailyAllowanceListToDailyAllowanceResponseList(dailyAllowances);

    return ResponseEntity.ok().body(dailyAllowanceResponses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DailyAllowanceResponse> getDailyAllowanceByID(@PathVariable Long id) {

    DailyAllowanceResponse dailyAllowance;

    try {
      dailyAllowance =
          dailyAllowanceMapper.mapDailyAllowanceToDailyAllowanceResponse(
              dailyAllowanceService.findById(id));
    } catch (DailyAllowanceNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

    return ResponseEntity.ok().body(dailyAllowance);
  }

  @GetMapping("/search")
  public ResponseEntity<DailyAllowanceResponse> getDailyAllowanceByRegion(
      @RequestParam String region) {

    DailyAllowanceResponse dailyAllowance =
        dailyAllowanceMapper.mapDailyAllowanceToDailyAllowanceResponse(
            dailyAllowanceService.findByRegion(region));

    return ResponseEntity.ok().body(dailyAllowance);
  }

  @PostMapping
  public ResponseEntity<DailyAllowanceResponse> createDailyAllowance(
      @RequestBody DailyAllowanceResponse dailyAllowanceResponse) {

    try {
      validateRegion(dailyAllowanceResponse.getRegion());
    } catch (RegionNullPointerException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    try {
      dailyAllowanceService.addDailyAllowance(
          dailyAllowanceMapper.mapDailyAllowanceResponseToDailyAllowance(dailyAllowanceResponse));
      DailyAllowance dailyAllowance =
          dailyAllowanceService.findByRegion(dailyAllowanceResponse.getRegion());

      return ResponseEntity.ok(
          dailyAllowanceMapper.mapDailyAllowanceToDailyAllowanceResponse(dailyAllowance));
    } catch (RegionAlreadyExistsException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<DailyAllowanceResponse> updateDailyAllowance(
      @RequestBody DailyAllowanceRequest newDailyAllowanceInfo, @PathVariable Long id) {

    try {
      validateRegion(newDailyAllowanceInfo.getRegion());
    } catch (RegionNullPointerException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    DailyAllowance daNew =
        dailyAllowanceMapper.mapDailyAllowanceRequestToDailyAllowanceUpdate(
            newDailyAllowanceInfo, id);

    DailyAllowance updatedDailyAllowance = dailyAllowanceService.updateDailyAllowance(daNew);

    return ResponseEntity.ok()
        .body(
            dailyAllowanceMapper.mapDailyAllowanceToDailyAllowanceResponse(updatedDailyAllowance));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteDailyAllowance(@PathVariable Long id) {

    try {
      dailyAllowanceService.deleteDailyAllowance(id);
    } catch (DailyAllowanceNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

    return ResponseEntity.noContent().build();
  }
}

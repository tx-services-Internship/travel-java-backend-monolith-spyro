package com.tx.travel.controller;

import com.tx.travel.mapper.DailyAllowanceMapper;
import com.tx.travel.model.DailyAllowance;
import com.tx.travel.payload.request.DailyAllowanceRequest;
import com.tx.travel.payload.response.DailyAllowanceResponse;
import com.tx.travel.service.DailyAllowanceService;
import com.tx.travel.service.exception.DailyAllowanceNotFoundException;
import com.tx.travel.service.exception.RegionAlreadyExistsException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

  public class RestResponse {

    private String response = null;
    private Object data;

    public RestResponse(Object data, String response) {
      this.data = data;
      this.response = response;
    }

    public RestResponse(Object data) {
      this.data = data;
    }
  }

  @GetMapping
  // @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or
  // hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<DailyAllowanceResponse>> getDailyAllowances() {

    List<DailyAllowance> dailyAllowances = dailyAllowanceService.getAllDailyAllowances();

    List<DailyAllowanceResponse> dailyAllowanceResponses =
        dailyAllowanceMapper.mapDailyAllowanceListToDailyAllowanceResponseList(dailyAllowances);

    return ResponseEntity.ok().body(dailyAllowanceResponses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getDailyAllowanceByID(@PathVariable Long id) {

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
  public ResponseEntity<DailyAllowanceRequest> updateDailyAllowance(
      @RequestBody DailyAllowanceRequest newDailyAllowanceInfo, @PathVariable Long id) {

    DailyAllowance daNew =
        dailyAllowanceMapper.mapDailyAllowanceRequestToDailyAllowanceUpdate(
            newDailyAllowanceInfo, id);

    DailyAllowance updatedDailyAllowance = dailyAllowanceService.updateDailyAllowance(daNew);

    return ResponseEntity.ok()
        .body(dailyAllowanceMapper.mapDailyAllowanceToDailyAllowanceRequest(updatedDailyAllowance));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity.HeadersBuilder<?> deleteDailyAllowance(@PathVariable Long id) {

    try {
      dailyAllowanceService.deleteDailyAllowance(id);
    } catch (DailyAllowanceNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    }

    return ResponseEntity.noContent();
  }
}

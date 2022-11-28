package com.tx.travel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tx.travel.mapper.DailyAllowanceMapper;
import com.tx.travel.model.DailyAllowance;
import com.tx.travel.payload.request.DailyAllowanceRequest;
import com.tx.travel.payload.response.DailyAllowanceResponse;
import com.tx.travel.payload.response.MessageResponse;
import com.tx.travel.service.DailyAllowanceService;
import com.tx.travel.service.exception.DailyAllowanceNotFoundException;
import com.tx.travel.service.exception.RegionAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.mariadb.jdbc.client.DataType.JSON;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/daily-allowances")
public class DailyAllowanceController {

    final DailyAllowanceService dailyAllowanceService;

    private final DailyAllowanceMapper dailyAllowanceMapper;

    public DailyAllowanceController(final DailyAllowanceService dailyAllowanceService, final DailyAllowanceMapper dailyAllowanceMapper) {
        this.dailyAllowanceMapper = dailyAllowanceMapper;
        this.dailyAllowanceService = dailyAllowanceService;
    }

    public class RestResponse{

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
    //@PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DailyAllowanceResponse>> getDailyAllowances() {

        List<DailyAllowance> dailyAllowances = dailyAllowanceService.getAllDailyAllowances();

        List<DailyAllowanceResponse> dailyAllowanceResponses = dailyAllowanceMapper.mapDailyAllowanceListToDailyAllowanceResponseList(dailyAllowances);

        return ResponseEntity.ok().body(dailyAllowanceResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDailyAllowanceByID(@PathVariable Long id) {

        DailyAllowanceResponse dailyAllowance;

        try {
            dailyAllowance = dailyAllowanceMapper.mapDailyAllowanceToDailyAllowanceResponse(dailyAllowanceService.findById(id));
        } catch (DailyAllowanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return ResponseEntity.ok().body(dailyAllowance);

    }

    @GetMapping("/search")
    public ResponseEntity<DailyAllowanceResponse> getDailyAllowanceByRegion(@RequestParam String region) {

        DailyAllowanceResponse dailyAllowance;

        dailyAllowance = dailyAllowanceMapper.mapDailyAllowanceToDailyAllowanceResponse(dailyAllowanceService.findByRegion(region));

        return ResponseEntity.ok().body(dailyAllowance);
    }

    @PostMapping
    public ResponseEntity<RestResponse> createDailyAllowance(@RequestBody DailyAllowanceResponse dailyAllowanceResponse) {
        try{

            dailyAllowanceService.addDailyAllowance(dailyAllowanceMapper.mapDailyAllowanceResponseToDailyAllowance(dailyAllowanceResponse));
            DailyAllowance dailyAllowance = dailyAllowanceService.findByRegion(dailyAllowanceResponse.getRegion());
            RestResponse dailyAllowanceRestResponse = new RestResponse(dailyAllowance, "Daily allowance successfully created!");

            return ResponseEntity.ok(dailyAllowanceRestResponse);
        }catch(RegionAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyAllowanceRequest> updateDailyAllowance(@RequestBody DailyAllowanceRequest newDailyAllowanceInfo, @PathVariable Long id){

        DailyAllowance daNew = dailyAllowanceMapper.mapDailyAllowanceRequestToDailyAllowanceUpdate(newDailyAllowanceInfo, id);

        DailyAllowance updatedDailyAllowance = dailyAllowanceService.updateDailyAllowance(daNew);

        return ResponseEntity.ok().body(dailyAllowanceMapper.mapDailyAllowanceToDailyAllowanceRequest(updatedDailyAllowance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDailyAllowance(@PathVariable Long id) {

        try{
            dailyAllowanceService.deleteDailyAllowance(id);
        } catch (DailyAllowanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return ResponseEntity.ok().body("Successfully removed daily allowance with id: " + id);
    }

}

package com.tx.travel.controller;

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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/daily-allowances")
public class DailyAllowanceController {

    final DailyAllowanceService dailyAllowanceService;

    public DailyAllowanceController(final DailyAllowanceService dailyAllowanceService) {
        this.dailyAllowanceService = dailyAllowanceService;
    }

    @GetMapping
    //@PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DailyAllowanceResponse>> getDailyAllowances() {

        List<DailyAllowanceResponse> dailyAllowances = dailyAllowanceService.getAllDailyAllowances();

        return ResponseEntity.ok().body(dailyAllowances);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDailyAllowanceByID(@PathVariable Long id) {

        DailyAllowanceResponse dailyAllowance;

        try {
            dailyAllowance = dailyAllowanceService.findById(id);
        } catch (DailyAllowanceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return ResponseEntity.ok().body(dailyAllowance);

    }

    @GetMapping("/search")
    public ResponseEntity<DailyAllowanceResponse> getDailyAllowanceByRegion(@RequestParam String region) {

        DailyAllowanceResponse dailyAllowance;

        try{
            dailyAllowance = dailyAllowanceService.findByRegion(region);
        }catch (DailyAllowanceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        return ResponseEntity.ok().body(dailyAllowance);
    }

    @PostMapping
    public ResponseEntity<?> createDailyAllowance(@RequestBody DailyAllowanceResponse dailyAllowanceResponse) {
        try{
            dailyAllowanceService.addDailyAllowance(dailyAllowanceResponse);

            return ResponseEntity.ok(new MessageResponse("Daily allowance successfully created!"));
        }catch(RegionAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyAllowanceRequest> updateDailyAllowance(@RequestBody DailyAllowanceRequest newDailyAllowanceInfo, @PathVariable Long id){

        DailyAllowanceRequest updatedDailyAllowance;

        updatedDailyAllowance = dailyAllowanceService.updateDailyAllowance(newDailyAllowanceInfo, id);

        return ResponseEntity.ok().body(updatedDailyAllowance);
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

package com.tx.travel.controller;

import com.tx.travel.payload.response.CostCentreDto;
import com.tx.travel.payload.response.DailyAllowanceDto;
import com.tx.travel.payload.response.ExchangeRateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/travel")
public class TravelController {
  @GetMapping("/daily-allowances")
  @PreAuthorize(
      "hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<DailyAllowanceDto>> getDailyAllowances() {
    return null;
  }

  @GetMapping("/cost-centres")
  @PreAuthorize(
      "hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<CostCentreDto>> getCostCentres() {
    return null;
  }

  @GetMapping("/exchange-rates")
  @PreAuthorize(
      "hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<ExchangeRateDto>> getDemoOfficeManager() {
    return null;
  }

  @PostMapping("/generate-pdf")
  @PreAuthorize(
      "hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<String> createPdf(final String html) {
    return null;
  }
}

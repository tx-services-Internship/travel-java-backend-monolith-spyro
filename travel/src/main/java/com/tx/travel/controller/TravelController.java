package com.tx.travel.controller;

import com.tx.travel.payload.response.CostCentreResponse;
import com.tx.travel.payload.response.DailyAllowanceResponse;
import com.tx.travel.payload.response.ExchangeRateResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/travel")
public class TravelController {
  @GetMapping("/daily-allowances")
  @PreAuthorize(
      "hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<DailyAllowanceResponse>> getDailyAllowances() {
    return null;
  }

  @GetMapping("/cost-centres")
  @PreAuthorize(
      "hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<CostCentreResponse>> getCostCentres() {
    return null;
  }

  @GetMapping("/exchange-rates")
  @PreAuthorize(
      "hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<ExchangeRateResponse>> getDemoOfficeManager() {
    return null;
  }

  @PostMapping("/generate-pdf")
  @PreAuthorize(
      "hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<String> createPdf(final String html) {
    return null;
  }
}

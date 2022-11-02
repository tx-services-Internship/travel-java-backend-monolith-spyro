package com.tx.travel.controller;

import com.tx.travel.payload.response.DemoResponse;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class DemoController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/employee")
  @PreAuthorize(
      "hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
  public DemoResponse getDemoEmployee() {
    return DemoResponse.builder().id(UUID.randomUUID()).build();
  }

  @GetMapping("/office-manager")
  @PreAuthorize("hasRole('ROLE_OFFICE_MANAGER') or hasRole('ROLE_ADMIN')")
  public DemoResponse getDemoOfficeManager() {
    return DemoResponse.builder().id(UUID.randomUUID()).build();
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
}

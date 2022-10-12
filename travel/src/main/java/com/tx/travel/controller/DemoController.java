package com.tx.travel.controller;

import static com.tx.travel.commons.oauth.security.ClientScopes.EMPLOYEE;
import static com.tx.travel.commons.oauth.security.ClientScopes.OFFICE_MANAGER;

import com.tx.travel.model.DemoDto;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/demo")
public class DemoController {

  @GetMapping()
  public DemoDto getDemo() {
    return DemoDto.builder().id(UUID.randomUUID()).build();
  }

  @GetMapping("/employee")
  @PreAuthorize("hasAuthority('" + EMPLOYEE + "')")
  public DemoDto getDemoEmployee() {
    return DemoDto.builder().id(UUID.randomUUID()).build();
  }

  @GetMapping("/office-manager")
  @PreAuthorize("hasAuthority('" + OFFICE_MANAGER + "')")
  public DemoDto getDemoOfficeManager() {
    return DemoDto.builder().id(UUID.randomUUID()).build();
  }
}

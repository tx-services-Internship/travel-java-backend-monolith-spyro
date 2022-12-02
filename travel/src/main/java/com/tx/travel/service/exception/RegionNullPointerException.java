package com.tx.travel.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Is null pointer")
public class RegionNullPointerException extends RuntimeException {

  public RegionNullPointerException() {
    super("Error: Region is null pointer!");
  }
}

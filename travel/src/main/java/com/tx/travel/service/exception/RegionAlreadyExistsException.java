package com.tx.travel.service.exception;

import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Already exists")
public class RegionAlreadyExistsException extends RuntimeException {

  public RegionAlreadyExistsException(final String region) {
    super(MessageFormat.format("Error: Region {0} is already inserted!", region));
  }
}

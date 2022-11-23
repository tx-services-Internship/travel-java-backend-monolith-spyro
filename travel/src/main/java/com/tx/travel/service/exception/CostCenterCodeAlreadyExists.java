package com.tx.travel.service.exception;

import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Already exists")
public class CostCenterCodeAlreadyExists extends RuntimeException{

  public CostCenterCodeAlreadyExists(final String code) {
    super(MessageFormat.format("Error: Code {0} already exists!", code));
  }

}

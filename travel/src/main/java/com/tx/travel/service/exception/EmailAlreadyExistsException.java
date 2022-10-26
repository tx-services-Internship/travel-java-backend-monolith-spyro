package com.tx.travel.service.exception;

import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Already exists")
public class EmailAlreadyExistsException extends RuntimeException {

  public EmailAlreadyExistsException(final String email) {
    super(MessageFormat.format("Error: Email {0} is already in use!", email));
  }
}

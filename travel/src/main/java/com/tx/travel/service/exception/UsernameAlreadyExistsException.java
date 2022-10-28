package com.tx.travel.service.exception;

import java.text.MessageFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Already exists")
public class UsernameAlreadyExistsException extends RuntimeException {

  public UsernameAlreadyExistsException(final String username) {
    super(MessageFormat.format("Error: Username {0} is already taken!", username));
  }
}

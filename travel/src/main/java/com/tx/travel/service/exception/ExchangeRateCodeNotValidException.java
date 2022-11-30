package com.tx.travel.service.exception;

import java.text.MessageFormat;

public class ExchangeRateCodeNotValidException extends RuntimeException {
  public ExchangeRateCodeNotValidException(String code) {
    super(MessageFormat.format("Code {0} is not valid", code));
  }
}

package com.tx.travel.service.exception;

import java.text.MessageFormat;

public class ExchangeRateNotFoundException extends RuntimeException {

  public ExchangeRateNotFoundException(Long id) {
    super(MessageFormat.format("Exchange rate with ID: {0} not found!", id));
  }
}

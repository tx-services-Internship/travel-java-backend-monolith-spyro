package com.tx.travel.service.exception;

import java.text.MessageFormat;

public class ExchangeRateAlreadyDefinedException extends RuntimeException {

  public ExchangeRateAlreadyDefinedException(String code) {
    super(
        MessageFormat.format(
            "Exchange rate for {0} is already defined! Update an existing one.", code));
  }
}

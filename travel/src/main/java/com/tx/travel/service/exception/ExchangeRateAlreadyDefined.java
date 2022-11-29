package com.tx.travel.service.exception;

import java.text.MessageFormat;

public class ExchangeRateAlreadyDefined extends RuntimeException {

  public ExchangeRateAlreadyDefined(String code) {
    super(
        MessageFormat.format(
            "Exchange rate for {0} is already defined! Update an existing one.", code));
  }
}

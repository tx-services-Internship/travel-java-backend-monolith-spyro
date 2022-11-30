package com.tx.travel.service.exception;

public class ExchangeRateAmountInRsdNotValidException extends RuntimeException {

  public ExchangeRateAmountInRsdNotValidException() {
    super("Amount in RSD is not valid.");
  }
}

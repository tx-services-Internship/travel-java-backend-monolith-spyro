package com.tx.travel.mapper;

import com.tx.travel.model.ExchangeRate;
import com.tx.travel.payload.request.ExchangeRatePostRequest;
import com.tx.travel.payload.request.ExchangeRatePutRequest;
import com.tx.travel.payload.response.ExchangeRateResponse;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateMapper {

  public ExchangeRate mapExchangeRatePostRequestToExchangeRate(
      ExchangeRatePostRequest exchangeRatePostRequest) {

    return ExchangeRate.builder()
        .code(exchangeRatePostRequest.getCode())
        .amountInRsd(exchangeRatePostRequest.getAmountInRsd())
        .build();
  }

  public ExchangeRate mapExchangeRatePutRequestToExchangeRate(
      ExchangeRatePutRequest exchangeRatePutRequest, Long id) {

    return ExchangeRate.builder()
        .id(id)
        .amountInRsd(exchangeRatePutRequest.getAmountInRsd())
        .build();
  }

  public ExchangeRateResponse mapExchangeRateToExchangeRateResponse(ExchangeRate exchangeRate) {

    return ExchangeRateResponse.builder()
        .id(exchangeRate.getId())
        .code(exchangeRate.getCode())
        .amountInRsd(exchangeRate.getAmountInRsd())
        .build();
  }
}

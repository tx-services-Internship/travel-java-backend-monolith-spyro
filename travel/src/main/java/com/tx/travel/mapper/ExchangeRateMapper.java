package com.tx.travel.mapper;

import com.tx.travel.model.ExchangeRate;
import com.tx.travel.payload.request.ExchangeRateRequest;
import com.tx.travel.payload.response.ExchangeRateResponse;

public class ExchangeRateMapper {

    public ExchangeRate mapExchangeRateRequestToExchangeRate(ExchangeRateRequest exchangeRateRequest){

        return ExchangeRate.builder()
                .code(exchangeRateRequest.getCode())
                .amountInRsd(exchangeRateRequest.getAmountInRsd())
                .build();
    }

    public ExchangeRateResponse mapExchangeRateToExchangeRateResponse(ExchangeRate exchangeRate){

        return ExchangeRateResponse.builder()
                .id(exchangeRate.getId())
                .code(exchangeRate.getCode())
                .amountInRsd(exchangeRate.getAmountInRsd())
                .build();
    }
}

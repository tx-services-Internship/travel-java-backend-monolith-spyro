package com.tx.travel.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRatePostRequest {

    private final String code;
    private final Float amountInRsd;

}

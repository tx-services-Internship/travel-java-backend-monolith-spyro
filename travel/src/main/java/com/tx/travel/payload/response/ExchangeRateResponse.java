package com.tx.travel.payload.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRateResponse {

  private final Long id;
  private final String code;
  private final BigDecimal amountInRsd;
}

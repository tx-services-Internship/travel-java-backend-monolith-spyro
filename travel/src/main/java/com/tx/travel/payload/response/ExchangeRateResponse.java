package com.tx.travel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ExchangeRateResponse {

  private final Long id;
//  private final String updatedAt;
  private final String code;
  private final Float amountInRsd;
}

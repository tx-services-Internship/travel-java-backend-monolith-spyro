package com.tx.travel.payload.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExchangeRateResponse {

  private final UUID id;
  private final String updatedAt;
  private final String name;
  private final String code;
  private final String amountInRsd;
}

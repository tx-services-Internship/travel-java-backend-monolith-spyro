package com.tx.travel.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ExchangeRateDto {

  private final UUID id;
  private final String updatedAt;
  private final String name;
  private final String code;
  private final String amountInRsd;
}

package com.tx.travel.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DailyAllowanceDto {

  private final UUID id;
  private final String region;
  private final long amount;
}

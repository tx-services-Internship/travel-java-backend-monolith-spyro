package com.tx.travel.payload.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyAllowanceResponse {

  private final UUID id;
  private final String region;
  private final long amount;
}

package com.tx.travel.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyAllowanceResponse {

  private final Long id;
  private final String region;
  private final double amount;

  public DailyAllowanceResponse(final Long id, final String region, final double amount) {
    this.region = region;
    this.amount = amount;
    this.id = id;
  }

  public DailyAllowanceResponse() {
    this.region = null;
    this.amount = 0;
    this.id = null;
  }

}

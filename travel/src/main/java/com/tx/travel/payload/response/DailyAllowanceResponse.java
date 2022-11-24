package com.tx.travel.payload.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DailyAllowanceResponse {

  private final Long id;
  private final String region;
  private final BigDecimal amount;

  public DailyAllowanceResponse(final Long id, final String region, final BigDecimal amount) {
    this.region = region;
    this.amount = amount;
    this.id = id;
  }

  public DailyAllowanceResponse() {
    this.region = null;
    this.amount = null;
    this.id = null;
  }

}

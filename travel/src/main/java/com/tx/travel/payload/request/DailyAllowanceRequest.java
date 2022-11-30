package com.tx.travel.payload.request;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyAllowanceRequest {

  private final String region;
  private final BigDecimal amount;

  public DailyAllowanceRequest(String region, BigDecimal amount) {

    this.region = region;
    this.amount = amount;
  }

  public DailyAllowanceRequest() {

    this.region = null;
    this.amount = null;
  }
}

package com.tx.travel.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CostCenterResponse {

  private final Long id;
  private final String code;
  private final String name;
}

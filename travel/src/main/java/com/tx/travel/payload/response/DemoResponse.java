package com.tx.travel.payload.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemoResponse {

  public UUID id;
}

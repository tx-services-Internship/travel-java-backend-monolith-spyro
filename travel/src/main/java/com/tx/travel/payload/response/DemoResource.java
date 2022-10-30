package com.tx.travel.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DemoResource {

  public UUID id;
}

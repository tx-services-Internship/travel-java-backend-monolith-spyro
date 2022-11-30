package com.tx.travel.payload.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CostCenterRequest {

  @NotBlank private String code;

  @NotBlank private String name;
}

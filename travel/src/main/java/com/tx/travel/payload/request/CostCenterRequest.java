package com.tx.travel.payload.request;


import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CostCenterRequest {


  @NotBlank
  private String code;

  @NotBlank
  private String name;

}

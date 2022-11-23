package com.tx.travel.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CostCenterRequest {

  private String code;
  private String name;

}

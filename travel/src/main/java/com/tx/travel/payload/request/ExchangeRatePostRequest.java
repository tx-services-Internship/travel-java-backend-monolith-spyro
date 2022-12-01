package com.tx.travel.payload.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRatePostRequest {

  @Length(min = 3, max = 3)
  private String code;

  @NotNull private BigDecimal amountInRsd;
}

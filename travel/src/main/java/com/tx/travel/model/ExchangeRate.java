package com.tx.travel.model;

import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(
    name = "exchange_rates",
    uniqueConstraints = {@UniqueConstraint(columnNames = "code")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Length(min = 3, max = 3)
  private String code;

  @NotNull private BigDecimal amountInRsd;
}

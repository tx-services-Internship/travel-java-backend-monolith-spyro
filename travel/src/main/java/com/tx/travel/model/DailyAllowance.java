package com.tx.travel.model;

import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Table(
    name = "daily_allowances",
    uniqueConstraints = {@UniqueConstraint(columnNames = "region")})
public class DailyAllowance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @NotBlank
  @Size(max = 100)
  @Setter
  @Getter
  private String region;

  @NotNull @Setter @Getter private BigDecimal amount;

  public DailyAllowance() {}

  public DailyAllowance(String region, BigDecimal amount) {
    this.region = region;
    this.amount = amount;
  }

  public DailyAllowance(Long id, String region, BigDecimal amount) {
    this.region = region;
    this.amount = amount;
    this.id = id;
  }
}

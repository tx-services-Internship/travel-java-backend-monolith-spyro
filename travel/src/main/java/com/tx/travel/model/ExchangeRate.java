package com.tx.travel.model;

import jdk.jfr.Unsigned;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exchange_rates",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "currency")
})
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Length(max = 20)
    private String currency;

    @NotNull
    @Unsigned
    private Float rate;

}

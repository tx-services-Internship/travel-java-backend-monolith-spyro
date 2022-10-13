package com.tx.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TravelApplication {
  public static void main(String[] args) {
    SpringApplication.run(TravelApplication.class, args);
  }
}

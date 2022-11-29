package com.tx.travel.repository;

import com.tx.travel.model.ExchangeRate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

  List<ExchangeRate> findAll();

  Optional<ExchangeRate> findById(Long id);

  Optional<ExchangeRate> findByCode(String code);
}

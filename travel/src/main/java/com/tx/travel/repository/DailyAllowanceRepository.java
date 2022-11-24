package com.tx.travel.repository;

import com.tx.travel.model.DailyAllowance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DailyAllowanceRepository extends JpaRepository<DailyAllowance, Long> {

    Optional<DailyAllowance> findByRegion(String region);

    List<DailyAllowance> findAll();

    Optional<DailyAllowance> findById(Long id);

}

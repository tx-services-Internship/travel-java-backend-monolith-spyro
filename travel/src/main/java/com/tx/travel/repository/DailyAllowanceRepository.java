package com.tx.travel.repository;

import com.tx.travel.model.DailyAllowance;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyAllowanceRepository extends JpaRepository<DailyAllowance, Long> {

  Optional<DailyAllowance> findByRegion(String region);

  List<DailyAllowance> findAll();

  Optional<DailyAllowance> findById(Long id);
}

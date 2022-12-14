package com.tx.travel.repository;

import com.tx.travel.model.CostCenter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {
  Optional<CostCenter> findByCode(String code);
}

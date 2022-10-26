package com.tx.travel.repository;

import com.tx.travel.commons.oauth.security.ERole;
import com.tx.travel.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}

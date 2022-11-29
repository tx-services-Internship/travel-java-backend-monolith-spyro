package com.tx.travel.service;

import com.tx.travel.commons.oauth.security.ERole;
import com.tx.travel.model.Role;
import com.tx.travel.model.User;
import com.tx.travel.repository.RoleRepository;
import com.tx.travel.repository.UserRepository;
import com.tx.travel.service.exception.EmailAlreadyExistsException;
import com.tx.travel.service.exception.UsernameAlreadyExistsException;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;

  private final RoleRepository roleRepository;
  public static final String ERROR_ROLE_NOT_FOUND = "Error: Role is not found.";

  public AuthService(final UserRepository userRepository, final RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public void findByUsernameOrEmail(@NotNull final String username, @NotNull final String email)
      throws UsernameAlreadyExistsException,EmailAlreadyExistsException {
    final Optional<User> userByUsername = userRepository.findByUsername(username);
    if (userByUsername.isPresent()) {
      throw new UsernameAlreadyExistsException(username);
    }
    final Optional<User> userByEmail = userRepository.findByEmail(email);
    if (userByEmail.isPresent()) {
      throw new EmailAlreadyExistsException(email);
    }
  }

  public Role addRoleAdmin(final String strRole) {
    if (strRole == null) {
      return roleRepository
          .findByName(ERole.ROLE_EMPLOYEE)
          .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
    } else {
      switch (strRole) {
        case "admin" -> {
          return roleRepository
              .findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
        }
        case "mod" -> {
          return roleRepository
              .findByName(ERole.ROLE_OFFICE_MANAGER)
              .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
        }
        default -> {
          return roleRepository
              .findByName(ERole.ROLE_EMPLOYEE)
              .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
        }
      }
    }
  }

  public Role addRole(final ERole role) {
      return roleRepository
              .findByName(role)
              .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
  }

  public User addUser(final User user) {
    return userRepository.save(user);
  }
}

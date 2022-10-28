package com.tx.travel.service;

import com.tx.travel.commons.oauth.security.ERole;
import com.tx.travel.model.Role;
import com.tx.travel.model.User;
import com.tx.travel.repository.RoleRepository;
import com.tx.travel.repository.UserRepository;
import com.tx.travel.service.exception.UsernameAlreadyExistsException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
      throws UsernameAlreadyExistsException {
    final Optional<User> userByUsername = userRepository.findByUsername(username);
    if (userByUsername.isPresent()) {
      throw new UsernameAlreadyExistsException(username);
    }
    final Optional<User> userByEmail = userRepository.findByEmail(email);
    if (userByEmail.isPresent()) {
      throw new UsernameAlreadyExistsException(email);
    }
  }

  public Set<Role> addRoles(final Set<String> strRoles) {
    final Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      final Role userRole =
          roleRepository
              .findByName(ERole.ROLE_EMPLOYEE)
              .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
      roles.add(userRole);
    } else {
      strRoles.forEach(
          role -> {
            switch (role) {
              case "admin":
                final Role adminRole =
                    roleRepository
                        .findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
                roles.add(adminRole);

                break;
              case "mod":
                final Role modRole =
                    roleRepository
                        .findByName(ERole.ROLE_OFFICE_MANAGER)
                        .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
                roles.add(modRole);

                break;
              default:
                final Role userRole =
                    roleRepository
                        .findByName(ERole.ROLE_EMPLOYEE)
                        .orElseThrow(() -> new RuntimeException(ERROR_ROLE_NOT_FOUND));
                roles.add(userRole);
            }
          });
    }
    return roles;
  }

  public User addUser(final User user) {
    return userRepository.save(user);
  }
}

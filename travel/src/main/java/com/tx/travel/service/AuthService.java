package com.tx.travel.service;

import com.tx.travel.commons.oauth.security.ERole;
import com.tx.travel.model.Role;
import com.tx.travel.model.User;
import com.tx.travel.repository.RoleRepository;
import com.tx.travel.repository.UserRepository;
import com.tx.travel.service.exception.EmailAlreadyExistsException;
import com.tx.travel.service.exception.RoleNotFoundException;
import com.tx.travel.service.exception.UsernameAlreadyExistsException;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  public AuthService(final UserRepository userRepository, final RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public void findByUsernameOrEmail(@NotNull final String username, @NotNull final String email)
      throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
    final Optional<User> userByUsername = userRepository.findByUsername(username);
    if (userByUsername.isPresent()) {
      throw new UsernameAlreadyExistsException(username);
    }
    final Optional<User> userByEmail = userRepository.findByEmail(email);
    if (userByEmail.isPresent()) {
      throw new EmailAlreadyExistsException(email);
    }
  }

  public Role getRole(ERole role) {
    return roleRepository.findByName(role).orElseThrow(() -> new RoleNotFoundException(role));
  }

  public User addUser(final User user) {
    return userRepository.save(user);
  }
}

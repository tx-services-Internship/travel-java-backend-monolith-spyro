package com.tx.travel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.tx.travel.AbstractUnitTestBase;
import com.tx.travel.commons.oauth.security.ERole;
import com.tx.travel.model.Role;
import com.tx.travel.model.User;
import com.tx.travel.repository.RoleRepository;
import com.tx.travel.repository.UserRepository;
import com.tx.travel.service.exception.EmailAlreadyExistsException;
import com.tx.travel.service.exception.RoleNotFoundException;
import com.tx.travel.service.exception.UsernameAlreadyExistsException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class AuthServiceImplementationTest extends AbstractUnitTestBase {
  @Mock UserRepository userRepository;

  @Mock RoleRepository roleRepository;

  @InjectMocks AuthService sut;

  @DisplayName(
      "given existing username"
          + " when user was searched"
          + " then throw UsernameAlreadyExistsException")
  @Test
  public void findByUsernameOrEmail_thrownUserNameAlreadyExistedException() {

    final String usr = "user1";

    when(userRepository.findByUsername(usr))
        .thenReturn(
            Optional.of(
                new User(
                    usr,
                    "email",
                    "123123123",
                    "User 1",
                    "Userovic",
                    "123123123",
                    "123123123",
                    1L)));

    assertThrows(
        UsernameAlreadyExistsException.class, () -> sut.findByUsernameOrEmail(usr, "email"));
  }

  @DisplayName(
      "given existing email"
          + " when user was searched"
          + " then throw EmailAlreadyExistsException")
  @Test
  public void findByUsernameOrEmail_thrownEmailAlreadyExistedException() {

    final String email = "usr1@email.com";

    when(userRepository.findByEmail(email))
        .thenReturn(
            Optional.of(
                new User(
                    "usr",
                    email,
                    "123123123",
                    "User 1",
                    "Userovic",
                    "123123123",
                    "123123123",
                    1L)));

    assertThrows(EmailAlreadyExistsException.class, () -> sut.findByUsernameOrEmail("", email));
  }

  @DisplayName("given string admin" + " when role was searched" + " then return admin role")
  @Test
  public void addRoleAdmin_success() {

    final Role role = new Role(ERole.ROLE_ADMIN);
    role.setId(2);

    when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(role));

    final Role result = sut.getRole(ERole.ROLE_ADMIN);
    assertEquals(result, role);
  }

  @DisplayName("given string mod" + " when role was searched" + " then return office manager role")
  @Test
  public void addRoleOM_success() {

    final Role role = new Role(ERole.ROLE_OFFICE_MANAGER);
    role.setId(2);

    when(roleRepository.findByName(ERole.ROLE_OFFICE_MANAGER)).thenReturn(Optional.of(role));

    final Role result = sut.getRole(ERole.ROLE_OFFICE_MANAGER);
    assertEquals(result, role);
  }

  @DisplayName(
      "given any string different from admin and mod"
          + " when role was searched"
          + " then return employee role")
  @Test
  public void addRoleEmpl_success() {

    final Role role = new Role(ERole.ROLE_EMPLOYEE);
    role.setId(2);

    when(roleRepository.findByName(ERole.ROLE_EMPLOYEE)).thenReturn(Optional.of(role));

    final Role result = sut.getRole(ERole.ROLE_EMPLOYEE);
    assertEquals(result, role);
  }

  @DisplayName(
      "given null string" + " when role was searched" + " then throws RoleNotFoundException")
  @Test
  public void addRole_null() {

    when(roleRepository.findByName(null)).thenReturn(Optional.empty());

    assertThrows(RoleNotFoundException.class, () -> sut.getRole(null));
  }
}

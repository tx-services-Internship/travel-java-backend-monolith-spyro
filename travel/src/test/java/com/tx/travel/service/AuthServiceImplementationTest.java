package com.tx.travel.service;

import com.tx.travel.AbstractUnitTestBase;
import com.tx.travel.commons.oauth.security.ERole;
import com.tx.travel.model.Role;
import com.tx.travel.model.User;
import com.tx.travel.repository.RoleRepository;
import com.tx.travel.repository.UserRepository;
import com.tx.travel.service.exception.EmailAlreadyExistsException;
import com.tx.travel.service.exception.UsernameAlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.tx.travel.service.AuthService.ERROR_ROLE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthServiceImplementationTest extends AbstractUnitTestBase {
    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    AuthService sut;

    @DisplayName("given existing username" +
                 "when user was searched" +
                 "then throw UsernameAlreadyExistsException")
    @Test
    public void findByUsernameOrEmail_thrownUserNameAlreadyExistedException(){

        final String usr = "user1";

        when(userRepository.findByUsername(usr))
                .thenReturn(Optional.of(new User(usr, "email", "123123123", "User 1", "Userovic", "123123123", "123123123", 1l)));

        assertThrows(UsernameAlreadyExistsException.class,()->{ sut.findByUsernameOrEmail(usr,"email"); });
    }

    @DisplayName("given existing email" +
            "when user was searched" +
            "then throw EmailAlreadyExistsException")
    @Test
    public void findByUsernameOrEmail_thrownEmailAlreadyExistedException(){

        final String email = "usr1@email.com";

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(new User("usr", email, "123123123", "User 1", "Userovic", "123123123", "123123123", 1l)));

        assertThrows(EmailAlreadyExistsException.class,()->{ sut.findByUsernameOrEmail("",email); });
    }

    @DisplayName("given string admin" +
            "when role was searched" +
            "then return admin role")
    @Test
    public void addRoleAdmin_success(){

        final String role = "admin";

        final Role rola = new Role(ERole.ROLE_ADMIN);
        rola.setId(2);

        when(roleRepository.findByName(ERole.ROLE_ADMIN))
                .thenReturn(Optional.of(rola));

        final Role result = sut.addRoleAdmin(role);
        assertEquals(result, rola);
    }

    @DisplayName("given string mod" +
            "when role was searched" +
            "then return office manager role")
    @Test
    public void addRoleOM_success(){

        final String role_string = "mod";

        final Role role= new Role(ERole.ROLE_OFFICE_MANAGER);
        role.setId(2);

        when(roleRepository.findByName(ERole.ROLE_OFFICE_MANAGER))
                .thenReturn(Optional.of(role));

        final Role result = sut.addRoleAdmin(role_string);

        assertEquals(result, role);
    }

    @DisplayName("given any string different from admin and mod" +
            "when role was searched" +
            "then return employee role")
    @Test
    public void addRoleEmpl_success(){

        final String role_string = "test_role"; //nesto sto nije admin/mod

        final Role role = new Role(ERole.ROLE_EMPLOYEE);
        role.setId(2);

        when(roleRepository.findByName(ERole.ROLE_EMPLOYEE))
                .thenReturn(Optional.of(role));

        final Role result = sut.addRoleAdmin(role_string);

        assertEquals(result, role);
    }

    @DisplayName("given null string" +
            "when role was searched" +
            "then return employee role")
    @Test
    public void addRole_null(){

        final String role = null;

        final Role rola = new Role(ERole.ROLE_EMPLOYEE);
        rola.setId(2);

        when(roleRepository.findByName(ERole.ROLE_EMPLOYEE))
                .thenReturn(Optional.of(rola));

        final Role result = sut.addRoleAdmin(null);

        assertEquals(result, rola);
    }

    @DisplayName("given non-existing role" +
            "when role was searched" +
            "then throw RuntimeException")
    @Test
    public void addRoleEmplException_success(){

        final String role = null;

        when(roleRepository.findByName(ERole.ROLE_EMPLOYEE))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> { sut.addRoleAdmin(null);});
    }

}

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

    @DisplayName("given auth service" +
                 "when auth service is asked to find user" +
                 "then auth service found user")
    @Test
    public void findByUsernameOrEmail_thrownUserNameAlreadyExistedException(){

        final String usr = "user1";

        when(userRepository.findByUsername(usr))
                .thenReturn(Optional.of(new User(usr, "email", "123123123", "User 1", "Userovic", "123123123", "123123123", 1l)));

        assertThrows(UsernameAlreadyExistsException.class,()->{ sut.findByUsernameOrEmail(usr,"email"); });
    }

    @Test
    public void findByUsernameOrEmail_thrownEmailAlreadyExistedException_success(){

        final String email = "usr1@email.com";

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(new User("usr", email, "123123123", "User 1", "Userovic", "123123123", "123123123", 1l)));

        assertThrows(EmailAlreadyExistsException.class,()->{ sut.findByUsernameOrEmail("",email); });
    }

    @Test
    public void addRoleAdmin_retAdmin_success(){

        final String role = "admin";

        final Role rola = new Role(ERole.ROLE_ADMIN);
        rola.setId(2);

        when(roleRepository.findByName(ERole.ROLE_ADMIN))
                .thenReturn(Optional.of(rola));

        final Role result = sut.addRoleAdmin(role);

        assertEquals(result, rola);
    }

    @Test
    public void addRole_retOM_success(){

        final String role = "mod";

        final Role rola = new Role(ERole.ROLE_OFFICE_MANAGER);
        rola.setId(2);

        when(roleRepository.findByName(ERole.ROLE_OFFICE_MANAGER))
                .thenReturn(Optional.of(rola));

        final Role result = sut.addRoleAdmin(role);

        assertEquals(result, rola);
    }

    @Test
    public void addRole_retEmpl_success(){

        final String role = "afa"; //nesto sto nije admin/mod

        final Role rola = new Role(ERole.ROLE_EMPLOYEE);
        rola.setId(2);

        when(roleRepository.findByName(ERole.ROLE_EMPLOYEE))
                .thenReturn(Optional.of(rola));

        final Role result = sut.addRoleAdmin(role);

        assertEquals(result, rola);
    }

    @Test
    public void addRole_retEmplNull_success(){

        final String role = null;//nesto sto nije admin/mod

        final Role rola = new Role(ERole.ROLE_EMPLOYEE);
        rola.setId(2);

        when(roleRepository.findByName(ERole.ROLE_EMPLOYEE))
                .thenReturn(Optional.of(rola));

        final Role result = sut.addRoleAdmin(null);

        assertEquals(result, rola);
    }

    @Test
    public void addRole_retEmplException_success(){

        final String role = null;

        when(roleRepository.findByName(ERole.ROLE_EMPLOYEE))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> { sut.addRoleAdmin(null);});
    }

}

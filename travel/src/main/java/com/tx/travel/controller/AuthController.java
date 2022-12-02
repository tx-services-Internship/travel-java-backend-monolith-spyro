package com.tx.travel.controller;

import com.tx.travel.commons.oauth.security.ERole;
import com.tx.travel.model.Role;
import com.tx.travel.model.User;
import com.tx.travel.payload.request.LoginRequest;
import com.tx.travel.payload.request.SignupRequest;
import com.tx.travel.payload.response.MessageResponse;
import com.tx.travel.payload.response.UserInfoResponse;
import com.tx.travel.security.jwt.JwtUtils;
import com.tx.travel.security.services.UserDetailsImpl;
import com.tx.travel.service.AuthService;
import com.tx.travel.service.exception.EmailAlreadyExistsException;
import com.tx.travel.service.exception.UsernameAlreadyExistsException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  final AuthenticationManager authenticationManager;

  final AuthService authService;

  final PasswordEncoder encoder;

  final JwtUtils jwtUtils;

  public AuthController(
      final AuthenticationManager authenticationManager,
      final AuthService authService,
      final PasswordEncoder encoder,
      final JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.authService = authService;
    this.encoder = encoder;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/signin")
  public ResponseEntity<UserInfoResponse> authenticateUser(
      @Valid @RequestBody final LoginRequest loginRequest) {

    final Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    final ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    final List<String> roles =
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(
            new UserInfoResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getPassportNo(),
                userDetails.getIdNo(),
                userDetails.getName(),
                userDetails.getSurname(),
                userDetails.getCostCenterId(),
                roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody final SignupRequest signUpRequest) {

    try {
      authService.findByUsernameOrEmail(signUpRequest.getUsername(), signUpRequest.getEmail());

    } catch (final UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
    }

    // Create new user's account
    final User user =
        new User(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()),
            signUpRequest.getName(),
            signUpRequest.getSurname(),
            signUpRequest.getPassportNo(),
            signUpRequest.getIdNo(),
                signUpRequest.getCostCenterId());

    final Role role = authService.getRole(ERole.ROLE_EMPLOYEE);

    user.setRole(role);

    authService.addUser(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<MessageResponse> logoutUser() {
    final ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }
}

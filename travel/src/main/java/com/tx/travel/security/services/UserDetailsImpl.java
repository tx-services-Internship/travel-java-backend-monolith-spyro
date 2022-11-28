package com.tx.travel.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tx.travel.model.User;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;

  private String passport_no;
  private String id_no;
  private String name;
  private String surname;
  private Long cost_center_id;

  @JsonIgnore private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(
      Long id,
      String username,
      String email,
      String passport_no,
      String id_no,
      String name,
      String surname,
      Long cost_center_id,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.passport_no = passport_no;
    this.id_no = id_no;
    this.name = name;
    this.surname = surname;
    this.cost_center_id = cost_center_id;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName().name()));

    return new UserDetailsImpl(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassport_no(),
        user.getId_no(),
        user.getName(),
        user.getSurname(),
        user.getCost_center_id(),
        user.getPassword(),
        authorities);
  }

  public String getPassport_no() {
    return passport_no;
  }

  public String getId_no() {
    return id_no;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public Long getCost_center_id() {
    return cost_center_id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email, password, authorities);
  }
}

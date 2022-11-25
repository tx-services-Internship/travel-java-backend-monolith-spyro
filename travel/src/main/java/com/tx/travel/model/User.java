package com.tx.travel.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "passport_no"),
                @UniqueConstraint(columnNames = "id_no")
        })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  @Size(max = 20)
  private String name;

  @NotBlank
  @Size(max = 20)
  private String surname;

  @NotBlank
  @Size(min=8, max=10)
  private String passport_no;
  @NotBlank
  @Size(min=8, max=9)
  private String id_no;
  @NotNull
  private Long cost_center_id;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private Role role;

  public User() {}

  public User(String username, String email, String password, String name, String surname, String passport_no, String id_no, Long cost_center_id) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.name = name;
    this.surname = surname;
    this.passport_no = passport_no;
    this.id_no = id_no;
    this.cost_center_id = cost_center_id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getPassport_no() {
    return passport_no;
  }

  public void setPassport_no(String passport_no) {
    this.passport_no = passport_no;
  }

  public String getId_no() {
    return id_no;
  }

  public void setId_no(String id_no) {
    this.id_no = id_no;
  }

  public Long getCost_center_id() {
    return cost_center_id;
  }

  public void setCost_center_id(Long cost_center_id) {
    this.cost_center_id = cost_center_id;
  }

}

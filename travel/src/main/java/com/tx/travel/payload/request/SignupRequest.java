package com.tx.travel.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private String role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  @NotBlank
  @Size(min = 8, max = 10)
  private String passport_no;

  @NotBlank
  @Size(min = 8, max = 9)
  private String id_no;

  @NotBlank
  @Size(max = 20)
  private String name;

  @NotBlank
  @Size(max = 20)
  private String surname;

  @NotNull private Long cost_center_id;

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

  public Long getCost_center_id() {
    return cost_center_id;
  }

  public void setCost_center_id(Long cost_center_id) {
    this.cost_center_id = cost_center_id;
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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}

package com.tx.travel.payload.response;

import java.util.List;

public class UserInfoResponse {
  private Long id;
  private String username;
  private String email;

  private String passport_no;
  private String id_no;
  private String name;
  private String surname;
  private Long cost_center_id;

  private List<String> roles;

  public UserInfoResponse(Long id, String username, String email, String passport_no, String id_no, String name, String surname, Long cost_center_id, List<String> roles) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.passport_no = passport_no;
    this.id_no = id_no;
    this.name = name;
    this.surname = surname;
    this.cost_center_id = cost_center_id;
    this.roles = roles;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }
}

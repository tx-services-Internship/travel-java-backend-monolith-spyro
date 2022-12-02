package com.tx.travel.payload.response;

import java.util.List;

public class UserInfoResponse {
  private Long id;
  private String username;
  private String email;

  private String passportNo;
  private String idNo;
  private String name;
  private String surname;
  private Long costCenterId;

  private List<String> roles;

  public UserInfoResponse(
      Long id,
      String username,
      String email,
      String passportNo,
      String idNo,
      String name,
      String surname,
      Long costCenterId,
      List<String> roles) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.passportNo = passportNo;
    this.idNo = idNo;
    this.name = name;
    this.surname = surname;
    this.costCenterId = costCenterId;
    this.roles = roles;
  }

  public String getPassportNo() {
    return passportNo;
  }

  public void setPassportNo(String passportNo) {
    this.passportNo = passportNo;
  }

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public Long getCostCenterId() {
    return costCenterId;
  }

  public void setCostCenterId(Long costCenterId) {
    this.costCenterId = costCenterId;
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

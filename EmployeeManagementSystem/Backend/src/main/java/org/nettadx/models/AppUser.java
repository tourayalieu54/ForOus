package org.nettadx.models;

import jakarta.persistence.*;

@Entity
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String emailAddress;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String role;

  @OneToOne
  @JoinColumn(name = "id")
  private Employee employee;

  public String getRole() {
    return role;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee){
    this.employee = employee;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }



}

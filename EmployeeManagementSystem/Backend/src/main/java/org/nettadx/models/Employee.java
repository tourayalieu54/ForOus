package org.nettadx.models;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "email_address", unique = true, nullable = false)
  private String emailAddress;

  @Column(name="position")
  private String position;

  public Employee() {}

  public Employee(String firstName, String middleName, String lastName, String emailAddress, String position) {
    super();
    this.position = position;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
  }

  public String getPosition(){
    return position;
  }
  public void setPosition(String position){
    this.position = position;
  }
  public String getMiddleName(){
    return middleName;
  }
  public void setMiddleName(String middleName){
    this.middleName = middleName;
  }
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getEmailAddress() {
    return emailAddress;
  }
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
}


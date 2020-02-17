package model;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "USER_ID", updatable = false, nullable = false)
  private long userId;
  @Column(name = "USER_NAME")
  private String userName;
  @Column(name = "EMAIL_ADDRESS")
  private String emailAddress;
  @Column(name = "PHONE_NUMBER")
  private String phoneNumber;

  public User() {
  }

  public User(String userName, String emailAddress, String phoneNumber) {
    this.userName = userName;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
  }

  public long getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}

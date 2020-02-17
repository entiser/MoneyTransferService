package model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNT")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ACCOUNT_ID")
  private long accountId;
  @Column(name = "USER_ID")
  private long userId;
  @Column(name = "ACCOUNT_BALANCE")
  private BigDecimal accountBalance;

  public Account() {
  }

  public Account(long accountId, long userId, BigDecimal accountBalance) {
    this.accountId = accountId;
    this.userId = userId;
    this.accountBalance = accountBalance;
  }

  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long accountId) {
    this.accountId = accountId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public BigDecimal getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(BigDecimal accountBalance) {
    this.accountBalance = accountBalance;
  }
}

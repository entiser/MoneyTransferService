package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import model.Account;
import model.Transaction;
import model.User;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize
public class GenericResponseDTO {

  private String status;
  private int code;
  private Long userId;
  private User user;
  private List<User> users;
  private Account account;
  private List<Account> accounts;
  private Long accountId;
  private String error;
  private Long transactionId;
  private List<Transaction> transactions;

  public GenericResponseDTO(String status, int code, Long userId, User user, List<User> users, Account account, List<Account> accounts, Long accountId, String error, Long transactionId, List<Transaction> transactions) {
    this.status = status;
    this.code = code;
    this.userId = userId;
    this.user = user;
    this.users = users;
    this.account = account;
    this.accounts = accounts;
    this.accountId = accountId;
    this.error = error;
    this.transactionId = transactionId;
    this.transactions = transactions;
  }

  public GenericResponseDTO() {
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public GenericResponseDTO setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
    return this;
  }

  public Long getTransactionId() {
    return transactionId;
  }

  public GenericResponseDTO setTransactionId(Long transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  public int getCode() {
    return code;
  }

  public GenericResponseDTO setCode(int code) {
    this.code = code;
    return this;
  }

  public String getError() {
    return error;
  }

  public GenericResponseDTO setError(String error) {
    this.error = error;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public GenericResponseDTO setStatus(String status) {
    this.status = status;
    return this;
  }

  public Long getUserId() {
    return userId;
  }

  public GenericResponseDTO setUserId(Long userId) {
    this.userId = userId;
    return this;
  }

  public User getUser() {
    return user;
  }

  public GenericResponseDTO setUser(User user) {
    this.user = user;
    return this;
  }

  public List<User> getUsers() {
    return users;
  }

  public GenericResponseDTO setUsers(List<User> users) {
    this.users = users;
    return this;
  }

  public Account getAccount() {
    return account;
  }

  public GenericResponseDTO setAccount(Account account) {
    this.account = account;
    return this;
  }

  public List<Account> getAccounts() {
    return accounts;
  }

  public GenericResponseDTO setAccounts(List<Account> accounts) {
    this.accounts = accounts;
    return this;
  }

  public Long getAccountId() {
    return accountId;
  }

  public GenericResponseDTO setAccountId(Long accountId) {
    this.accountId = accountId;
    return this;
  }
}

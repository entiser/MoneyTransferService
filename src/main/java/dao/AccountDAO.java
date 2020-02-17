package dao;

import model.Account;
import model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDAO {

  Account getAccountById(long accountId) throws Exception;
  List<Account> getAllAccounts() throws Exception;

  long createAccount(Account account) throws Exception;

  BigDecimal getAccountBalance(long accountId);
  long transferBalance(Transaction transaction) throws Exception;
}

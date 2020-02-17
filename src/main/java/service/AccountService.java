package service;

import dao.AccountDAO;
import dao.impl.AccountDAOImpl;
import dto.GenericResponseDTO;
import model.Account;
import model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import java.util.List;

public class AccountService {

  private static final Logger LOGGER = LoggerFactory.getLogger("AccountService.class");
  private static AccountDAO accountDao = new AccountDAOImpl();

  public GenericResponseDTO getAccount (long accountId) {

    try {
      return mapToGetAccountResponse(accountDao.getAccountById(accountId));
    } catch (NoResultException e) {
      LOGGER.error("Invalid accountId");
      return new GenericResponseDTO().setCode(404).setStatus("Failed").setError("Invalid accountId");
    } catch (Exception e) {
      LOGGER.error("Error occurred while fetching account",e);
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }
  public GenericResponseDTO getAccounts() {

    try {
      return mapToGetAccountsResponse(accountDao.getAllAccounts());
    } catch (Exception e) {
      LOGGER.error("Error occurred while fetching account",e);
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }
  public GenericResponseDTO createAccount(Account account) {
    try {
      return mapToCreateAccountResponse(accountDao.createAccount(account));
    } catch (Exception e) {
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }

  public GenericResponseDTO transferBalance(Transaction transaction) {
    try {
      return mapToBalanceTransferResponse(accountDao.transferBalance(transaction));
    } catch (Exception e) {
      if(e.getMessage().equalsIgnoreCase("Insufficient Balance"))
        return new GenericResponseDTO().setCode(404).setStatus("Failed").setError(e.getMessage());
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }


  private GenericResponseDTO mapToGetAccountResponse(Account account) {
    return new GenericResponseDTO()
        .setStatus("Success")
        .setCode(200)
        .setAccount(account);
  }

  private GenericResponseDTO mapToGetAccountsResponse(List<Account> accounts) {
    return new GenericResponseDTO()
        .setStatus("Success")
        .setCode(200)
        .setAccounts(accounts);
  }

  private GenericResponseDTO mapToCreateAccountResponse(long accountId) {
    return new GenericResponseDTO()
        .setStatus("Success")
        .setCode(200)
        .setAccountId(accountId);
  }

  private GenericResponseDTO mapToBalanceTransferResponse(long transactionId) {
    return new GenericResponseDTO()
        .setStatus("Success")
        .setCode(200)
        .setTransactionId(transactionId);
  }

}

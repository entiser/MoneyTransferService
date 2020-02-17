package service;

import dao.AccountDAO;
import dao.TransactionDAO;
import dao.impl.AccountDAOImpl;
import dao.impl.TransactionDAOImpl;
import dto.GenericResponseDTO;
import model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TransactionService {

  private static final Logger LOGGER = LoggerFactory.getLogger("TransactionService.class");
  private static TransactionDAO transactionDAO = new TransactionDAOImpl();

  public GenericResponseDTO getTransactionsByAccountId(long accountId) {
    try {
      return mapToGetTransactionsResponse(transactionDAO.getTransactionByAccountId(accountId));
    } catch (Exception e) {
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }

  public GenericResponseDTO getAllTransactions() {
    try {
      return mapToGetTransactionsResponse(transactionDAO.getAllTransactions());
    } catch (Exception e) {
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }

  private GenericResponseDTO mapToGetTransactionsResponse(List<Transaction> transactions) {
    return new GenericResponseDTO()
        .setCode(200)
        .setStatus("Success")
        .setTransactions(transactions);
  }
}

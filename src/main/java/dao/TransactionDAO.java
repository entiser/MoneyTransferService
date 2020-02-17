package dao;

import model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDAO {

  List<Transaction> getTransactionByAccountId(long accountId) throws Exception;
  List<Transaction> getAllTransactions() throws Exception;
  long createTransaction(Transaction transaction) throws Exception;


}

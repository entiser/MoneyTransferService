package dao.impl;

import constants.AppConstants;
import dao.AccountDAO;
import datastore.DataStore;
import datastore.DataStoreFactory;
import dao.TransactionDAO;
import exceptions.InvalidTransaction;
import model.Account;
import model.Transaction;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.AppUtil;

import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountDAOImpl.class);
  private static TransactionDAO transactionDAO = new TransactionDAOImpl();
  private static DataStore dataStore;

  public DataStore getDataStore() {
    return dataStore;
  }

  public AccountDAOImpl() {
    dataStore = DataStoreFactory.DataStores.getDataStoreByName(AppUtil.getStringProperty("db.default.name")).getStoreImpl();
  }

  @Override
  public Account getAccountById(long accountId) throws Exception{
    Session session = getDataStore().getSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
    Root<Account> userRecord = criteriaQuery.from(Account.class);
    criteriaQuery.select(userRecord).where(criteriaBuilder.equal(userRecord.get(AppConstants.AccountEntityConstants.ACCOUNT_ID), accountId));
    Query query = session.createQuery(criteriaQuery);
    return (Account) query.getSingleResult();
  }

  @Override
  public List<Account> getAllAccounts() throws Exception{
    Session session = getDataStore().getSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
    Root<Account> accountRecord = criteriaQuery.from(Account.class);
    criteriaQuery.select(accountRecord);
    Query query = session.createQuery(criteriaQuery);
    return (List<Account>) query.getResultList();
  }

  @Override
  public long createAccount(Account account) throws Exception{
    Session session = getDataStore().getSession();
    try {
      session.getTransaction().begin();
      session.save(account);
      session.getTransaction().commit();
      return account.getAccountId();
    } catch (IllegalStateException | RollbackException e) {
      LOGGER.error("Error occurred while saving account", e);
      session.getTransaction().rollback();
      throw e;
    } finally {
      session.clear();
    }
  }

  @Override
  public BigDecimal getAccountBalance(long accountId) {
    Session session = getDataStore().getSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
    Root<Account> userRecord = criteriaQuery.from(Account.class);
    criteriaQuery.select(userRecord).where(criteriaBuilder.equal(userRecord.get(AppConstants.AccountEntityConstants.ACCOUNT_ID), accountId));
    Query query = session.createQuery(criteriaQuery);
    return ((Account) query.getSingleResult()).getAccountBalance();
  }

  private BigDecimal setAccountBalance(long accountId, BigDecimal accountBalance) {
    Session session = getDataStore().getSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
    Root<Account> userRecord = criteriaQuery.from(Account.class);
    criteriaQuery.select(userRecord).where(criteriaBuilder.equal(userRecord.get(AppConstants.AccountEntityConstants.ACCOUNT_ID), accountId));
    Query query = session.createQuery(criteriaQuery);
    return ((Account) query.getSingleResult()).getAccountBalance();
  }

  @Override
  @Transactional
  public long transferBalance(Transaction transaction) throws Exception {
    Session session = null;

    try {
      session = getDataStore().getSession();

      final Account senderAccount = session.get(Account.class, transaction.getSenderAccountId(), LockMode.PESSIMISTIC_WRITE);
      final Account receiverAccount = session.get(Account.class, transaction.getReceiverAccountId(), LockMode.PESSIMISTIC_WRITE);

      session.refresh(senderAccount);
      session.refresh(receiverAccount);

      session.getTransaction().begin();

      BigDecimal senderCurrentBalance = getAccountBalance(transaction.getSenderAccountId());
      BigDecimal receiverCurrentBalance = getAccountBalance(transaction.getReceiverAccountId());
      if(transaction.getTransactionAmount().compareTo(senderCurrentBalance) > 0) {
        throw new InvalidTransaction("Insufficient Balance");
      }

      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

      CriteriaUpdate<Account> senderCriteriaUpdate = criteriaBuilder.createCriteriaUpdate(Account.class);
      Root<Account> senderRecord = senderCriteriaUpdate.from(Account.class);
      senderCriteriaUpdate.set(AppConstants.AccountEntityConstants.ACCOUNT_BALANCE, senderCurrentBalance.subtract(transaction.getTransactionAmount()));
      senderCriteriaUpdate.where(session.getCriteriaBuilder().equal(senderRecord.get(AppConstants.AccountEntityConstants.ACCOUNT_ID), transaction.getSenderAccountId()));

      CriteriaUpdate<Account> receiverCriteriaUpdate = criteriaBuilder.createCriteriaUpdate(Account.class);
      Root<Account> receiverRecord = receiverCriteriaUpdate.from(Account.class);
      receiverCriteriaUpdate.set(AppConstants.AccountEntityConstants.ACCOUNT_BALANCE, receiverCurrentBalance.add(transaction.getTransactionAmount()));
      receiverCriteriaUpdate.where(session.getCriteriaBuilder().equal(receiverRecord.get(AppConstants.AccountEntityConstants.ACCOUNT_ID), transaction.getReceiverAccountId()));

      session.createQuery(senderCriteriaUpdate).executeUpdate();
      session.createQuery(receiverCriteriaUpdate).executeUpdate();

      long transactionId =  transactionDAO.createTransaction(transaction);

      session.getTransaction().commit();

      return transactionId;

    } catch (Exception e) {
      session.getTransaction().rollback();
      LOGGER.error("Transaction Failed");
      throw e;
    } finally {
      if(session!=null)
        session.clear();
    }
  }


}

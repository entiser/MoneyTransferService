package dao.impl;

import constants.AppConstants;
import datastore.DataStore;
import datastore.DataStoreFactory;
import dao.TransactionDAO;
import model.Transaction;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.AppUtil;

import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionDAOImpl.class);
  private static final long INVALID_ID = -1L;
  private static DataStore dataStore;

  public DataStore getDataStore() {
    return dataStore;
  }

  public TransactionDAOImpl() {
    dataStore = DataStoreFactory.DataStores.getDataStoreByName(AppUtil.getStringProperty("db.default.name")).getStoreImpl();
  }

  @Override
  public List<Transaction> getTransactionByAccountId(long accountId) throws Exception{
    Session session = getDataStore().getSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
    Root<Transaction> transactionRecord = criteriaQuery.from(Transaction.class);

    Predicate predicateForSender = criteriaBuilder.equal(transactionRecord.get(AppConstants.TransactionEntityConstants.SENDER_ACCOUNT_ID), accountId);
    Predicate predicateForReceiver = criteriaBuilder.equal(transactionRecord.get(AppConstants.TransactionEntityConstants.RECEIVER_ACCOUNT_BALANCE), accountId);
    Predicate predicateForSenderOrReceiver = criteriaBuilder.or(predicateForSender, predicateForReceiver);
    criteriaQuery.select(transactionRecord).where(predicateForSenderOrReceiver);
    Query query = session.createQuery(criteriaQuery);
    return (List<Transaction>) query.getResultList();
  }

  @Override
  public List<Transaction> getAllTransactions() throws Exception{
    Session session = getDataStore().getSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
    Root<Transaction> transactionRecord = criteriaQuery.from(Transaction.class);

    criteriaQuery.select(transactionRecord);
    Query query = session.createQuery(criteriaQuery);
    return (List<Transaction>) query.getResultList();
  }

  @Override
  public long createTransaction(Transaction transaction)  throws Exception{
    Session session = getDataStore().getSession();
    try {
      session.getTransaction().begin();
      session.save(transaction);
      session.getTransaction().commit();
      return transaction.getTransactionId();
    } catch (IllegalStateException | RollbackException e) {
      LOGGER.error("Error occurred while saving transaction", e);
      session.getTransaction().rollback();
      throw e;
    } finally {
      session.clear();
    }
  }
}

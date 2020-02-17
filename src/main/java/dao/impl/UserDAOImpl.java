package dao.impl;

import constants.AppConstants;
import datastore.DataStore;
import datastore.DataStoreFactory;
import dao.UserDAO;
import model.User;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.AppUtil;

import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.criteria.*;
import java.util.List;

public class UserDAOImpl implements UserDAO {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);
  private static final long INVALID_ID = -1L;
  private static DataStore dataStore;

  public DataStore getDataStore() {
    return dataStore;
  }

  public UserDAOImpl() {
    dataStore = DataStoreFactory.DataStores.getDataStoreByName(AppUtil.getStringProperty("db.default.name")).getStoreImpl();


  }

  @Override
  public User getUser(long userId) throws Exception {
      Session session = getDataStore().getSession();
      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
      Root<User> userRecord = criteriaQuery.from(User.class);
      criteriaQuery.select(userRecord).where(criteriaBuilder.equal(userRecord.get(AppConstants.UserEntityConstants.USER_ID), userId));
      Query query = session.createQuery(criteriaQuery);
      return (User) query.getSingleResult();
  }

  @Override
  public List<User> getAllUsers() throws Exception{
    Session session = getDataStore().getSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
    Root<User> userRecord = criteriaQuery.from(User.class);
    criteriaQuery.select(userRecord);
    Query query = session.createQuery(criteriaQuery);
    return (List<User>)query.getResultList();
  }

  @Override
  public long createUser(User user) throws Exception{
    Session session = getDataStore().getSession();
    try {
      session.getTransaction().begin();
      session.save(user);
      session.getTransaction().commit();
      return user.getUserId();
    } catch (IllegalStateException | RollbackException e) {
      LOGGER.error("Error occurred while saving user", e);
      session.getTransaction().rollback();
      throw e;
    } finally {
      session.clear();
    }
  }

  @Override
  public boolean updateUser(User user) throws Exception {
    Session session = getDataStore().getSession();
    try {
      CriteriaUpdate<User> criteriaUpdate = session.getCriteriaBuilder().createCriteriaUpdate(User.class);
      Root<User> root = criteriaUpdate.from(User.class);
      criteriaUpdate.set(AppConstants.UserEntityConstants.USER_NAME, user.getUserName());
      criteriaUpdate.set(AppConstants.UserEntityConstants.EMAIL_ADDRESS, user.getEmailAddress());
      criteriaUpdate.set(AppConstants.UserEntityConstants.PHONE_NUMBER, user.getPhoneNumber());
      criteriaUpdate.where(session.getCriteriaBuilder().equal(root.get(AppConstants.UserEntityConstants.USER_ID), user.getUserId()));


      session.getTransaction().begin();
      session.createQuery(criteriaUpdate).executeUpdate();
      session.getTransaction().commit();
      return true;
    } catch (IllegalStateException | RollbackException e) {
      LOGGER.error("Error occurred while updating user", e);
      session.getTransaction().rollback();
      throw e;
    } finally {
      session.clear();
    }
  }

  @Override
  public boolean deleteUser(long userId) throws Exception {
    Session session = getDataStore().getSession();
    try {
      CriteriaDelete<User> criteriaDelete = session.getCriteriaBuilder().createCriteriaDelete(User.class);
      Root<User> root = criteriaDelete.from(User.class);
      criteriaDelete.where(session.getCriteriaBuilder().equal(root.get(AppConstants.UserEntityConstants.USER_ID), userId));


      session.getTransaction().begin();
      session.createQuery(criteriaDelete).executeUpdate();
      session.getTransaction().commit();
      return true;
    } catch (IllegalStateException | RollbackException e) {
      LOGGER.error("Error occurred while deleting user", e);
      session.getTransaction().rollback();
      throw e;
    } finally {
      session.clear();
    }
  }
}

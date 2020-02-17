package datastore;

import dao.AccountDAO;
import dao.UserDAO;
import org.hibernate.Session;

public abstract class DataStore {

  public abstract Session getSession();
  public abstract UserDAO getUserDAO();
 public abstract AccountDAO getAccountDAO();

}

package module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import controller.UserController;
import dao.AccountDAO;
import dao.TransactionDAO;
import dao.impl.TransactionDAOImpl;
import datastore.H2DataStore;
import dao.UserDAO;
import dao.impl.AccountDAOImpl;
import dao.impl.UserDAOImpl;

public class AppModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(H2DataStore.class).in(Singleton.class);
    bind(UserController.class).in(Singleton.class);
    bind(UserDAO.class).to(UserDAOImpl.class);
    bind(AccountDAO.class).to(AccountDAOImpl.class);
    bind(TransactionDAO.class).to(TransactionDAOImpl.class);
  }
}

package datastore;

import configs.HibernateConfig;
import dao.AccountDAO;
import dao.UserDAO;
import dao.impl.AccountDAOImpl;
import dao.impl.UserDAOImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class H2DataStore extends DataStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(H2DataStore.class);
  private static final String H2 = "h2";

  private static UserDAO userDAO = new UserDAOImpl();

  private static AccountDAO accountDAO = new AccountDAOImpl();

  private static SessionFactory sessionFactory;

  @Inject
  public H2DataStore() {
    sessionFactory = HibernateConfig.getSessionFactory(H2);
  }

  public Session getSession(){
    return sessionFactory.openSession();

  }

  public UserDAO getUserDAO() {
    return userDAO;
  }

  public AccountDAO getAccountDAO() {
    return accountDAO;
  }


//  @Override
//  public DataStoreFactory addTestData() {
//    LOGGER.info("Populating Test User Table and data ..... ");
//    Connection conn = null;
//    try {
//      conn = H2DataStore.getConnection();
//      RunScript.execute(conn, new FileReader("src/test/resources/demo.sql"));
//    } catch (SQLException e) {
//      LOGGER.error("populateTestData(): Error populating user data: ", e);
//      throw new RuntimeException(e);
//    } catch (FileNotFoundException e) {
//      LOGGER.error("populateTestData(): Error finding test script file ", e);
//      throw new RuntimeException(e);
//    } finally {
//      DbUtils.closeQuietly(conn);
//    }
//    return null;
//  }


}

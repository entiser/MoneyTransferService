package configs;


import model.Account;
import model.Transaction;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.AppUtil;

import java.util.Properties;

public class HibernateConfig {
  
  private static final Logger LOGGER = LoggerFactory.getLogger("HibernateConfig.class");
  
  private static SessionFactory sessionFactory;

  public static SessionFactory getSessionFactory(String db) {
    if(sessionFactory == null) {
      try{

        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, AppUtil.getStringProperty("db."+db+".driver"));
        properties.put(Environment.URL, AppUtil.getStringProperty("db."+db+".jdbcUrl"));
        properties.put(Environment.USER, AppUtil.getStringProperty("db."+db+".user"));
        properties.put(Environment.PASS, AppUtil.getStringProperty("db."+db+".pass"));
        properties.put(Environment.DIALECT, AppUtil.getStringProperty("db."+db+".dialect"));
        properties.put(Environment.SHOW_SQL, AppUtil.getStringProperty("db."+db+".showSql"));
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "create");
        properties.put(Environment.C3P0_MIN_SIZE, AppUtil.getIntegerProperty("db."+db+".cp.minSize", 1));
        properties.put(Environment.C3P0_MAX_SIZE, AppUtil.getIntegerProperty("db."+db+".cp.maxSize", 1));
        properties.put(Environment.C3P0_ACQUIRE_INCREMENT, AppUtil.getIntegerProperty("db."+db+".cp.acquireIncrement", 1));
        properties.put(Environment.C3P0_TIMEOUT, AppUtil.getIntegerProperty("db."+db+".cp.timeout", 1800));
        properties.put(Environment.C3P0_MAX_STATEMENTS, AppUtil.getIntegerProperty("db."+db+".cp.maxStatements", 1));
        properties.put(Environment.JPA_LOCK_TIMEOUT, 10000);

        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(Transaction.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties())
            .build();

//        SchemaExport schemaExport = new SchemaExport();
//        schemaExport.setDelimiter(";");

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);


      } catch (Exception e) {
        LOGGER.error("Unable to connect to data store");
      }
    }

    return sessionFactory;
  }

}

package constants;

import util.AppUtil;

public class Path {

  private static final String VERSION = "/"+AppUtil.getStringProperty("api.version");

  public static class User {
    public static final String USER_INDEX_WITH_ID = VERSION+"/users/:id/";
    public static final String USER_INDEX = VERSION+"/users/";
  }

  public static class Account {
    public static final String ACCOUNT_INDEX_WITH_ID = VERSION+"/accounts/:id/";
    public static final String ACCOUNT_INDEX = VERSION+"/accounts/";
    public static final String ACCOUNT_TRANSFER = VERSION+"/accounts/transfer/";
  }

  public static class Transaction {
    public static final String TRANSACTION_INDEX_WITH_ID = VERSION+"/transactions/:id/";
    public static final String TRANSACTION_INDEX = VERSION+"/transactions/";
  }

}

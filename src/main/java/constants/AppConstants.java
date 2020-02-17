package constants;

public class AppConstants {

  public static class UserEntityConstants {
    public static String USER_ID = "userId";
    public static String USER_NAME = "userName";
    public static String EMAIL_ADDRESS = "emailAddress";
    public static String PHONE_NUMBER = "phoneNumber";
  }

  public static class AccountEntityConstants {
    public static String USER_ID = "userId";
    public static String ACCOUNT_ID = "accountId";
    public static String ACCOUNT_BALANCE = "accountBalance";
  }

  public static class TransactionEntityConstants {
    public static String SENDER_ACCOUNT_ID = "senderAccountId";
    public static String RECEIVER_ACCOUNT_BALANCE = "receiverAccountId";
  }

}

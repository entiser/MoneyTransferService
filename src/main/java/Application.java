import com.google.inject.Guice;
import constants.Path;
import controller.AccountController;
import controller.TransactionController;
import controller.UserController;
import module.AppModule;
import util.AppUtil;

import static spark.Spark.*;



public class Application {
  public static void main(String[] args) {

    Guice.createInjector(new AppModule())
        .getInstance(Application.class)
        .run();



  }

  private void run() {
    port(AppUtil.getIntegerProperty("app.http.port", 4567));
    staticFiles.expireTime(600L);

    // USER ROUTES
    get(Path.User.USER_INDEX_WITH_ID,  UserController.getUserById);
    get(Path.User.USER_INDEX,          UserController.getUsers);
    post(Path.User.USER_INDEX,         UserController.createUser);
    put(Path.User.USER_INDEX,          UserController.updateUser);
    delete(Path.User.USER_INDEX_WITH_ID, UserController.deleteUser);

    //ACCOUNT ROUTES
    get(Path.Account.ACCOUNT_INDEX_WITH_ID,  AccountController.getAccountById);
    get(Path.Account.ACCOUNT_INDEX,          AccountController.getAccounts);
    post(Path.Account.ACCOUNT_INDEX,         AccountController.createAccount);
    post(Path.Account.ACCOUNT_TRANSFER,         AccountController.transferBalance);

    //TRANSACTION ROUTES
    get(Path.Transaction.TRANSACTION_INDEX_WITH_ID,  TransactionController.getTransactionsByAccountId);
    get(Path.Transaction.TRANSACTION_INDEX,          TransactionController.getTransactions);
  }
}

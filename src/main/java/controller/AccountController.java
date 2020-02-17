package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.GenericResponseDTO;
import model.Account;
import model.Transaction;
import model.User;
import service.AccountService;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class AccountController {

  private static AccountService accountService = new AccountService();
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static Route getAccountById = (Request request, Response response) -> {
    long id = Long.parseLong(request.params(":id"));
    GenericResponseDTO responseDTO =  accountService.getAccount(id);
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };

  public static Route getAccounts = (Request request, Response response) -> {
    GenericResponseDTO responseDTO =  accountService.getAccounts();
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };

  public static Route createAccount = (Request request, Response response) -> {
    Account account = objectMapper.readValue(request.body(), Account.class);
    GenericResponseDTO responseDTO = accountService.createAccount(account);
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };

  public static Route transferBalance = (Request request, Response response) -> {
    Transaction transaction = objectMapper.readValue(request.body(), Transaction.class);
    GenericResponseDTO responseDTO = accountService.transferBalance(transaction);
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };


}

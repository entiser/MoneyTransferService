package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.GenericResponseDTO;
import service.AccountService;
import service.TransactionService;
import spark.Request;
import spark.Response;
import spark.Route;

public class TransactionController {

  private static TransactionService transactionService = new TransactionService();
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static Route getTransactionsByAccountId = (Request request, Response response) -> {
    long id = Long.parseLong(request.params(":id"));
    GenericResponseDTO responseDTO =  transactionService.getTransactionsByAccountId(id);
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };

  public static Route getTransactions = (Request request, Response response) -> {
    GenericResponseDTO responseDTO =  transactionService.getAllTransactions();
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };
}

package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.GenericResponseDTO;
import model.User;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

  private static UserService userService = new UserService();
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static Route getUserById = (Request request, Response response) -> {
    long id = Long.parseLong(request.params(":id"));
    GenericResponseDTO responseDTO =  userService.getUser(id);
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };

  public static Route getUsers = (Request request, Response response) -> {
    GenericResponseDTO responseDTO =  userService.getUsers();
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };

  public static Route createUser = (Request request, Response response) -> {
    User user = objectMapper.readValue(request.body(), User.class);
    GenericResponseDTO responseDTO = userService.createUser(user);
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };

  public static Route updateUser = (Request request, Response response) -> {
    User user = objectMapper.readValue(request.body(), User.class);
    GenericResponseDTO responseDTO = userService.updateUser(user);
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };

  public static Route deleteUser = (Request request, Response response) -> {
    long id = Long.parseLong(request.params(":id"));
    GenericResponseDTO responseDTO = userService.deleteUser(id);
    response.status(responseDTO.getCode());
    response.body(objectMapper.writeValueAsString(responseDTO));
    response.type("application/json");
    return response.body();
  };





}

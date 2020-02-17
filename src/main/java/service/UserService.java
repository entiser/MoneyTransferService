package service;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import dto.GenericResponseDTO;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import java.util.List;

public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger("UserService.class");
  private static UserDAO userDAO = new UserDAOImpl();

  public GenericResponseDTO getUser (long userId) {

    try {
      return mapToGetUserResponse(userDAO.getUser(userId));
    } catch (NoResultException e) {
      LOGGER.error("Invalid userId");
      return new GenericResponseDTO().setCode(404).setStatus("Failed").setError("Invalid UserId");
    } catch (Exception e) {
      LOGGER.error("Error occurred while fetching user",e);
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }
  public GenericResponseDTO getUsers() {

    try {
      return mapToGetUsersResponse(userDAO.getAllUsers());
    } catch (Exception e) {
      LOGGER.error("Error occurred while fetching user",e);
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }
  public GenericResponseDTO createUser(User user) {
    try {
      return mapToCreateUserResponse(userDAO.createUser(user));
    } catch (Exception e) {
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }

  public GenericResponseDTO updateUser(User user) {
    try {
      return mapToUpdateNDeleteUserResponse(userDAO.updateUser(user));
    } catch (Exception e) {
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }

  public GenericResponseDTO deleteUser(long userId) {
    try {
      return mapToUpdateNDeleteUserResponse(userDAO.deleteUser(userId));
    } catch (Exception e) {
      return new GenericResponseDTO().setCode(500).setStatus("Failed").setError(e.getMessage());
    }
  }

  private GenericResponseDTO mapToGetUserResponse(User user) {
    return new GenericResponseDTO()
        .setStatus("Success")
        .setCode(200)
        .setUser(user);
  }

  private GenericResponseDTO mapToGetUsersResponse(List<User> users) {
    return new GenericResponseDTO()
        .setStatus("Success")
        .setCode(200)
        .setUsers(users);
  }

  private GenericResponseDTO mapToCreateUserResponse(long userId) {
    return new GenericResponseDTO()
        .setStatus("Success")
        .setCode(200)
        .setUserId(userId);
  }

  private GenericResponseDTO mapToUpdateNDeleteUserResponse(boolean status) {
    if(true == status) {
      return new GenericResponseDTO().setStatus("Success").setCode(200);
    }
    return new GenericResponseDTO().setCode(500).setStatus("Failed").setError("Failed to Update/Delete user");
  }




}

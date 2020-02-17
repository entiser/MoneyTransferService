package dao;

import exceptions.UserException;
import model.User;

import java.util.List;

public interface UserDAO {

  User getUser(long userId) throws Exception;
  List<User> getAllUsers() throws Exception;

  long createUser(User user) throws Exception;
  boolean updateUser(User user) throws Exception;
  boolean deleteUser(long userId) throws Exception;

}

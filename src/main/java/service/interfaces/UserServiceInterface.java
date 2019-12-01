package service.interfaces;

import dao.bean.User;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameHasExistedException;
import exceptions.WrongPasswordException;

public interface UserServiceInterface {

    User login(String username, String password) throws UserDoesNotExistException, WrongPasswordException;

    long register(User user) throws UserNameHasExistedException;

    void modifyProfile(User user);
}

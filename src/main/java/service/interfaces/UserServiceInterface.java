package service.interfaces;

import controller.servlets.user.UserBo;
import dao.bean.User;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameHasExistedException;
import exceptions.WrongPasswordException;

public interface UserServiceInterface {

    User login(String username, String password) throws UserDoesNotExistException, WrongPasswordException;

    void register(UserBo userBo) throws UserNameHasExistedException;

    void modifyProfile(User user);
}

package dao.interfaces;

import dao.bean.User;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameHasExistedException;

public interface UserDaoInterface {

    long addUser(User user) throws UserNameHasExistedException;

    User getUserById(long id);

    User getUserByName(String name) throws UserDoesNotExistException;

    void modifyUserProfile(User user) throws UserNameHasExistedException;
}

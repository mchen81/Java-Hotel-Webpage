package dao;

import dao.bean.User;
import dao.interfaces.UserDaoInterface;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameHasExistedException;

public class UserDao extends FinalProjectDao implements UserDaoInterface {
    @Override
    public long addUser(User user) throws UserNameHasExistedException {
        return 0;
    }

    @Override
    public User getUserById(long id) {
        return null;
    }

    @Override
    public User getUserByName(String name) throws UserDoesNotExistException {
        return null;
    }

    @Override
    public void modifyUserProfile(User user) throws UserNameHasExistedException {
    }
}

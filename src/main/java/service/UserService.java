package service;

import common.SQLErrorCode;
import dao.UserDao;
import dao.bean.User;
import dao.interfaces.UserDaoInterface;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameHasExistedException;
import exceptions.WrongPasswordException;
import service.interfaces.UserServiceInterface;

import java.sql.SQLException;

public class UserService implements UserServiceInterface {

    private UserDaoInterface userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    @Override
    public long login(String username, String password) throws UserDoesNotExistException, WrongPasswordException {
        User user = userDao.getUserByName(username);
        String hashedPassword = hashPassword(password);
        if (user == null) {
            throw new UserDoesNotExistException();
        } else if (!user.getKey().equals(hashedPassword)) {
            throw new WrongPasswordException();
        }
        return user.getId();
    }

    @Override
    public long register(User user) throws UserNameHasExistedException {
        try {
            return userDao.addUser(user);
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLErrorCode.DUPLICATE_ENTRY) {
                throw new UserNameHasExistedException();
            } else {
                return 0;
            }
        }
    }

    @Override
    public void modifyProfile(User user) {
        userDao.modifyUserProfile(user);
    }

    /**
     * TODO
     *
     * @return
     */
    private String hashPassword(String password) {
        return password;
    }
}

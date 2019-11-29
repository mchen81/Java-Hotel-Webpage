package service;

import dao.UserDao;
import dao.bean.User;
import dao.interfaces.UserDaoInterface;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameHasExistedException;
import exceptions.WrongPasswordException;
import service.interfaces.UserServiceInterface;

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
        long newUserId = userDao.addUser(user);
        return newUserId;
    }

    @Override
    public void modifyProfile(User user) throws UserNameHasExistedException {
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

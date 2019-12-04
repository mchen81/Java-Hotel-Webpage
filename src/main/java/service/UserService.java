package service;

import common.RandomNumberUtil;
import controller.servlets.user.UserBo;
import dao.UserDao;
import dao.bean.User;
import dao.interfaces.UserDaoInterface;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameHasExistedException;
import exceptions.WrongPasswordException;
import service.interfaces.UserServiceInterface;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService implements UserServiceInterface {

    private UserDaoInterface userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    @Override
    public User login(String username, String password) throws UserDoesNotExistException, WrongPasswordException {
        User user = userDao.getUserByName(username);
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        String hashedPassword = hashPassword(password, user.getSalt());
        if (!user.getHashPass().equals(hashedPassword)) {
            throw new WrongPasswordException();
        }
        userDao.updateLastLoginTime(user.getId());
        return user;
    }

    @Override
    public void register(UserBo userBo) throws UserNameHasExistedException {

        User user = new User();
        user.setName(userBo.getUsername());
        user.setSalt(generateRandomSalt());
        user.setHashPass(hashPassword(userBo.getPassword(), user.getSalt()));
        user.setEmailAddress(userBo.getEmail());
        userDao.addUser(user);
    }

    @Override
    public void modifyProfile(User user) {
        userDao.modifyUserProfile(user);
    }


    private String hashPassword(String password, String salt) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest((password + salt).getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            // return the HashText
            return hashtext;

        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm");
            return null;
        }

    }

    private String generateRandomSalt() {
        return RandomNumberUtil.generateRandomString(16);
    }

}

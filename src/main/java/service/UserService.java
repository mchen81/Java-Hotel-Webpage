package service;

import common.RandomNumberUtil;
import controller.servlets.user.UserBo;
import dao.SavedHotelDao;
import dao.UserDao;
import dao.bean.User;
import dao.interfaces.SaveHotelDaoInterface;
import dao.interfaces.UserDaoInterface;
import exceptions.HotelHasBeenSavedException;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameHasExistedException;
import exceptions.WrongPasswordException;
import service.interfaces.UserServiceInterface;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserService implements UserServiceInterface {

    private UserDaoInterface userDao;

    private SaveHotelDaoInterface saveHotelDao;

    private Map<String, Set<String>> userSavedHotels;

    public UserService() {
        this.userDao = new UserDao();
        this.saveHotelDao = new SavedHotelDao();
        userSavedHotels = saveHotelDao.getAllUserSavedHotel();
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

    @Override
    public Set<String> getSavedHotels(String userId) {
        if (userSavedHotels.containsKey(userId)) {
            return userSavedHotels.get(userId);
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public void addSavedHotel(String userId, String hotelId) throws HotelHasBeenSavedException {
        saveHotelDao.addUserSaveHotel(userId, hotelId);
        if (userSavedHotels.containsKey(userId)) {
            userSavedHotels.get(userId).add(hotelId);
        } else {
            HashSet<String> set = new HashSet<>();
            set.add(hotelId);
            userSavedHotels.put(userId, set);
        }
    }

    @Override
    public void removeOneSavedHotel(String userId, String hotelId) {
        saveHotelDao.removeOneSavedHotel(userId, hotelId);
        userSavedHotels.get(userId).remove(hotelId);
    }

    @Override
    public void clearSavedHotel(String userId) {
        saveHotelDao.clearUserSavedHotelById(userId);
        userSavedHotels.replace(userId, new HashSet<>());
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

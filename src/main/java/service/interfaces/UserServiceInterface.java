package service.interfaces;

import controller.servlets.user.UserBo;
import dao.bean.User;
import exceptions.HotelHasBeenSavedException;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameHasExistedException;
import exceptions.WrongPasswordException;

import java.util.Set;

public interface UserServiceInterface {

    User login(String username, String password) throws UserDoesNotExistException, WrongPasswordException;

    void register(UserBo userBo) throws UserNameHasExistedException;

    void modifyProfile(User user);

    Set<String> getSavedHotels(String userId);

    void addSavedHotel(String userId, String HotelId) throws HotelHasBeenSavedException;

    void removeOneSavedHotel(String userId, String hotelId);

    void clearSavedHotel(String userId);

}

package service.interfaces;

import controller.servlets.user.UserBo;
import dao.bean.User;
import exceptions.HotelHasBeenSavedException;
import exceptions.UserDoesNotExistException;
import exceptions.UserNameHasExistedException;
import exceptions.WrongPasswordException;

import java.util.Set;

public interface UserServiceInterface {

    /**
     * given username and password, examining the correctness
     * @param username user's name
     * @param password password
     * @return if login success, return the user;s profile. Otherwise exceptions
     * @throws UserDoesNotExistException
     * @throws WrongPasswordException
     */
    User login(String username, String password) throws UserDoesNotExistException, WrongPasswordException;

    /**
     * register a new user to database
     * @param userBo user's info from the front-end form
     * @throws UserNameHasExistedException when the username has been existing
     */
    void register(UserBo userBo) throws UserNameHasExistedException;

    /**
     * modify the user's info, provide password and email changed
     * @param user modified user's info
     */
    void modifyProfile(User user);

    /**
     * get the hotels saved by the user
     * @param userId
     * @return
     */
    Set<String> getSavedHotels(String userId);

    /**
     *
     * @param userId
     * @param HotelId
     * @throws HotelHasBeenSavedException when the user has saved the hotel
     */
    void addSavedHotel(String userId, String HotelId) throws HotelHasBeenSavedException;

    /**
     * remove one saved hotel
     * @param userId user's id
     * @param hotelId the hotel that user's wants to remove
     */
    void removeOneSavedHotel(String userId, String hotelId);

    /**
     * remove all saved hotels of a user
     * @param userId
     */
    void clearSavedHotel(String userId);

}

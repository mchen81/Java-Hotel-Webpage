package dao.interfaces;

import exceptions.HotelHasBeenSavedException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SaveHotelDaoInterface {

    /**
     *
     * @param userId
     * @param hotelId
     * @throws HotelHasBeenSavedException when the user has saved the hotel
     */
    void addUserSaveHotel(String userId, String hotelId) throws HotelHasBeenSavedException;

    /**
     * get the hotels id saved by user
     * @param userId
     * @return
     */
    Set<String> getUserSavedHotelById(String userId);

    /**
     * get the hotels saved by all users
     * @return
     */
    Map<String, Set<String>> getAllUserSavedHotel();

    /**
     *
     * @param userId
     */
    void clearUserSavedHotelById(String userId);

    /**
     *
     * @param userId
     * @param hotelId
     */
    void removeOneSavedHotel(String userId, String hotelId);
}

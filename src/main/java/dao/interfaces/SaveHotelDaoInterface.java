package dao.interfaces;

import exceptions.HotelHasBeenSavedException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SaveHotelDaoInterface {

    void addUserSaveHotel(String userId, String hotelId) throws HotelHasBeenSavedException;

    Set<String> getUserSavedHotelById(String userId);

    Map<String, Set<String>> getAllUserSavedHotel();

    void clearUserSavedHotelById(String userId);


}

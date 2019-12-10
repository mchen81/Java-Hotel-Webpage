package dao.interfaces;

import dao.bean.Hotel;

import java.util.List;

public interface HotelDaoInterface {

    /**
     * get hotels info from database
     * @param hotelId
     * @return
     */
    Hotel getHotelByHotelId(String hotelId);

    /**
     * get all hotels
     * @return
     */
    List<Hotel> getAllHotels();
}

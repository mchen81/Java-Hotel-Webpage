package dao.interfaces;

import dao.bean.Hotel;

import java.util.List;

public interface HotelDaoInterface {

    Hotel getHotelByHotelId(String hotelId);

    List<Hotel> getAllHotels();
}

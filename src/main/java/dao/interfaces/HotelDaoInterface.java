package dao.interfaces;

import dao.bean.Hotel;

import java.util.List;

public interface HotelDaoInterface {

    Hotel getHotelByHotelId(String hotelId);

    List<Hotel> getAllHotels();

    void addHotel(Hotel hotel);

    void modifyHotel(Hotel hotel);

    void deleteHotel(String hotelId);

    void addHotels(List<Hotel> hotels);
}

package service.interfaces;

import dao.bean.Hotel;

import java.util.List;

public interface HotelServiceInterface {

    Hotel findHotelById(String id);

    List<Hotel> findHotelsByKeyWords(String city, String name);

    void addHotel(Hotel hotel);

    void deleteHotel(String hotelId);
}

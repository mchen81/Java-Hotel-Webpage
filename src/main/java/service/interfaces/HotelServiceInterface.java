package service.interfaces;

import dao.bean.Hotel;
import service.bean.TouristAttraction;

import java.util.List;

public interface HotelServiceInterface {

    Hotel findHotelById(String id);

    List<Hotel> findHotelsByKeyWords(String city, String name);

    List<TouristAttraction> findTouristAttraction(String hotelId, double miles);
}

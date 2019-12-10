package service.interfaces;

import dao.bean.Hotel;
import service.bean.TouristAttraction;

import java.util.List;

public interface HotelServiceInterface {

    /**
     * find a hotel by hotel Id
     *
     * @param id hotel's id
     * @return hotel info
     */
    Hotel findHotelById(String id);

    /**
     * find hotels by key word, if name is not given, return the all hotels in the city
     *
     * @param city city, must be complete
     * @param name hotel's name, null is acceptable
     * @return a list of hotels
     */
    List<Hotel> findHotelsByKeyWords(String city, String name);

    /**
     * find tourist attraction around the hotel with miles
     *
     * @param hotelId hotel's id
     * @param miles   radius in miles
     * @return a list of TouristAttraction
     */
    List<TouristAttraction> findTouristAttraction(String hotelId, double miles);

    /**
     * get all hotels
     *
     * @return a list containing all hotels without sort
     */
    List<Hotel> findAllHotels();
}

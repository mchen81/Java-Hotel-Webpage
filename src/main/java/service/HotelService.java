package service;

import dao.HotelDao;
import dao.bean.Hotel;
import dao.interfaces.HotelDaoInterface;
import service.interfaces.HotelServiceInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelService implements HotelServiceInterface {

    private HotelDaoInterface hotelDao;

    private Map<String, Hotel> hotelsCache;

    public HotelService() {
        hotelDao = new HotelDao();
        hotelsCache = new HashMap<>();
        loadAllHotels();
    }

    @Override
    public Hotel findHotelById(String id) {
        return hotelsCache.get(id);
    }

    @Override
    public List<Hotel> findHotelsByKeyWords(String city, String name) {
        List<Hotel> result = new ArrayList<>();
        for (Hotel hotel : hotelsCache.values()) {
            if (hotel.getCity().equals(city) && hotel.getName().contains(name)) {
                result.add(hotel);
            }
        }
        return result;
    }

    @Override
    public void addHotel(Hotel hotel) {
        hotelDao.addHotel(hotel);
        hotelsCache.put(hotel.getId(), hotel);
    }

    @Override
    public void deleteHotel(String hotelId) {
        hotelDao.deleteHotel(hotelId);
        hotelsCache.remove(hotelId);
    }

    /**
     * load hotel data into hotels cache
     */
    private void loadAllHotels() {
        for (Hotel hotel : hotelDao.getAllHotels()) {
            hotelsCache.put(hotel.getId(), hotel);
        }
    }
}







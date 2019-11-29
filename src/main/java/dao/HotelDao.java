package dao;

import dao.bean.Hotel;
import dao.interfaces.HotelDaoInterface;

import java.util.List;

public class HotelDao extends FinalProjectDao implements HotelDaoInterface {
    @Override
    public Hotel getHotelByHotelId(String hotelId) {
        return null;
    }

    @Override
    public List<Hotel> getAllHotels() {
        return null;
    }

    @Override
    public void addHotel(Hotel hotel) {
    }

    @Override
    public void modifyHotel(Hotel hotel) {
    }

    @Override
    public void deleteHotel(String hotelId) {
    }

    @Override
    public void addHotels(List<Hotel> hotels) {
    }
}

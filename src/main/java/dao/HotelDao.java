package dao;

import dao.bean.Hotel;
import dao.interfaces.FinalProjectDao;
import dao.interfaces.HotelDaoInterface;

import java.util.ArrayList;
import java.util.List;

public class HotelDao extends FinalProjectDao implements HotelDaoInterface {
    @Override
    public Hotel getHotelByHotelId(String hotelId) {
        return new Hotel();
    }

    @Override
    public List<Hotel> getAllHotels() {
        return new ArrayList<>();
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

package dao;

import dao.bean.Hotel;
import dao.interfaces.FinalProjectDao;
import dao.interfaces.HotelDaoInterface;
import exceptions.QueryException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelDao extends FinalProjectDao implements HotelDaoInterface {

    private static final String CALL_GET_HOTEL_BY_ID = "{Call getHotelById(?)}";

    private static final String CALL_GET_ALL_HOTELS = "{Call getAllHotels()}";

    @Override
    public Hotel getHotelByHotelId(String hotelId) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_GET_HOTEL_BY_ID);
            callableStatement.setString(1, hotelId);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                Hotel hotel = new Hotel();
                hotel.setId(resultSet.getString(1));
                hotel.setName(resultSet.getString(2));
                hotel.setCountry(resultSet.getString(3));
                hotel.setState(resultSet.getString(4));
                hotel.setCity(resultSet.getString(5));
                hotel.setAddress(resultSet.getString(6));
                hotel.setLatitude(resultSet.getDouble(7));
                hotel.setLongitude(resultSet.getDouble(8));
                return hotel;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        }
    }

    @Override
    public List<Hotel> getAllHotels() {
        Connection connection = getConnection();
        List<Hotel> hotels = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_GET_ALL_HOTELS);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Hotel hotel = new Hotel();
                hotel.setId(resultSet.getString(1));
                hotel.setName(resultSet.getString(2));
                hotel.setCountry(resultSet.getString(3));
                hotel.setState(resultSet.getString(4));
                hotel.setCity(resultSet.getString(5));
                hotel.setAddress(resultSet.getString(6));
                hotel.setLatitude(resultSet.getDouble(7));
                hotel.setLongitude(resultSet.getDouble(8));
                hotels.add(hotel);
            }
            return hotels;
        } catch (SQLException e) {
            throw new QueryException();
        }
    }
}

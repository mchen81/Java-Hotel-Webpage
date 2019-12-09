package dao;

import dao.interfaces.FinalProjectDao;
import dao.interfaces.SaveHotelDaoInterface;
import exceptions.HotelHasBeenSavedException;
import exceptions.QueryException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SavedHotelDao extends FinalProjectDao implements SaveHotelDaoInterface {

    private static final String CALL_ADD_USER_SAVED_HOTEL = "{CALL addUserSaveHotel(?,?)}";

    private static final String CALL_GET_USER_SAVED_HOTEL_BY_ID = "{CALL getUserSavedHotelById(?)}";

    private static final String CALL_GET_ALL_USER_SAVED_HOTEL = "{CALL getAllUserSavedHotel()}";

    private static final String CALL_CLEAR_USER_SAVED_HOTEL_BY_ID = "{CALL clearUserSavedHotelById(?)}";

    private static final String CALL_REMOVE_USER_SAVED_HOTEL = "{Call removeUserSavedHotel(?,?)}";

    @Override
    public void addUserSaveHotel(String userId, String hotelId) throws HotelHasBeenSavedException {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_ADD_USER_SAVED_HOTEL);
            callableStatement.setString(1, userId);
            callableStatement.setString(2, hotelId);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new HotelHasBeenSavedException();
            } else {
                throw new QueryException(e.getErrorCode());
            }
        }
    }

    @Override
    public Set<String> getUserSavedHotelById(String userId) {
        Connection connection = getConnection();
        Set<String> result = new HashSet<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_GET_USER_SAVED_HOTEL_BY_ID);
            callableStatement.setString(1, userId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        }
        return result;
    }

    @Override
    public Map<String, Set<String>> getAllUserSavedHotel() {
        Connection connection = getConnection();
        Map<String, Set<String>> result = new HashMap<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_GET_ALL_USER_SAVED_HOTEL);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String userId = resultSet.getString(1);
                String hotelId = resultSet.getString(2);
                if (result.containsKey(userId)) {
                    result.get(userId).add(hotelId);
                } else {
                    HashSet<String> set = new HashSet<>();
                    set.add(hotelId);
                    result.put(userId, set);
                }
            }
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        }
        return result;
    }

    @Override
    public void clearUserSavedHotelById(String userId) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_CLEAR_USER_SAVED_HOTEL_BY_ID);
            callableStatement.setString(1, userId);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        }
    }

    @Override
    public void removeOneSavedHotel(String userId, String hotelId) {

        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_REMOVE_USER_SAVED_HOTEL);
            callableStatement.setString(1, userId);
            callableStatement.setString(2, hotelId);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        }
    }
}

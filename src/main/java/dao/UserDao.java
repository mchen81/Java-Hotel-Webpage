package dao;

import common.SQLErrorCode;
import dao.bean.User;
import dao.interfaces.FinalProjectDao;
import dao.interfaces.UserDaoInterface;
import exceptions.DBConnectionFailException;
import exceptions.UserNameHasExistedException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends FinalProjectDao implements UserDaoInterface {

    private static final String CALL_ADD_USER = "{Call addUser(?,?,?,?)}";
    private static final String CALL_GET_USER_BY_ID = "{Call getUserById(?)}";
    private static final String CALL_GET_USER_BY_NAME = "{Call getUserByName(?)}";
    private static final String CALL_UPDATE_USER_INFO = "{Call updateUser(?,?,?)}";

    @Override
    public long addUser(User user) throws UserNameHasExistedException {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_ADD_USER);
            callableStatement.setString(1, user.getName());
            callableStatement.setString(2, user.getKey());
            callableStatement.setString(3, user.getEmailAddress());
            callableStatement.execute();
            return callableStatement.getLong(4);
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLErrorCode.DUPLICATE_ENTRY) {
                throw new UserNameHasExistedException();
            } else {
                throw new DBConnectionFailException();
            }
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public User getUserById(long id) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_GET_USER_BY_ID);
            callableStatement.setLong(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(id);
                user.setEmailAddress(resultSet.getString("EMAIL"));
                user.setKey(resultSet.getString("KEY"));
                user.setName(resultSet.getString("USER_NAME"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DBConnectionFailException();
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public User getUserByName(String name) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_GET_USER_BY_NAME);
            callableStatement.setString(1, name);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setKey(resultSet.getString(3));
                user.setEmailAddress(resultSet.getString(4));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DBConnectionFailException();
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void modifyUserProfile(User user) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_UPDATE_USER_INFO);
            callableStatement.setLong(1, user.getId());
            callableStatement.setString(2, user.getKey());
            callableStatement.setString(3, user.getEmailAddress());
            callableStatement.execute();
        } catch (SQLException e) {
            throw new DBConnectionFailException();
        } finally {
            closeConnection(connection);
        }
    }
}

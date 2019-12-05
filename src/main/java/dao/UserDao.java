package dao;

import common.SQLErrorCode;
import dao.bean.User;
import dao.interfaces.FinalProjectDao;
import dao.interfaces.UserDaoInterface;
import exceptions.QueryException;
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
    private static final String CALL_UPDATE_LAST_LOGIN_TIME = "{Call updateLastLoginTime(?)}";

    @Override
    public void addUser(User user) throws UserNameHasExistedException {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_ADD_USER);
            callableStatement.setString(1, user.getName());
            callableStatement.setString(2, user.getHashPass());
            callableStatement.setString(3, user.getSalt());
            callableStatement.setString(4, user.getEmailAddress());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLErrorCode.DUPLICATE_ENTRY) {
                throw new UserNameHasExistedException();
            } else {
                System.out.println(e.getMessage());
                throw new QueryException(e.getErrorCode());

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
                user.setName(resultSet.getString(2));
                user.setHashPass(resultSet.getString(3));
                user.setSalt(resultSet.getString(4));
                user.setEmailAddress(resultSet.getString(5));
                user.setLastLoginTime(resultSet.getTimestamp(6));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public User getUserByName(String name) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_GET_USER_BY_NAME);
            callableStatement.setString("I_USER_NAME", name);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setHashPass(resultSet.getString(3));
                user.setSalt(resultSet.getString(4));
                user.setEmailAddress(resultSet.getString(5));
                user.setLastLoginTime(resultSet.getTimestamp(6));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
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
            callableStatement.setString(2, user.getHashPass());
            callableStatement.setString(3, user.getEmailAddress());
            callableStatement.execute();
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void updateLastLoginTime(long id) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_UPDATE_LAST_LOGIN_TIME);
            callableStatement.setLong(1, id);
            callableStatement.execute();
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        } finally {
            closeConnection(connection);
        }
    }
}

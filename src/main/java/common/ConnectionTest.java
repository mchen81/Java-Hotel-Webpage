package common;

import dao.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionTest {
    public static void testConnection() {
        Connection connection = null;
        try {
            connection = DaoUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select ? from dual");
            preparedStatement.setString(1, "HI");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.println("Successful Connection: " + resultSet.getString(1));
        } catch (SQLException e) {
            System.out.println("Connection fail: " + e.getCause());
            throw new IllegalArgumentException("Connection fail:" + e);
        }
    }
}

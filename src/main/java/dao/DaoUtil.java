package dao;

import exceptions.BlobConvertException;
import exceptions.ConnectionCloseException;
import exceptions.DBConnectionFailException;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoUtil extends FinalProjectDao {

    private static final String driver = "com.mysql.cj.jdbc.Driver";

    private static final String jdbcURL = "jdbc:mysql://localhost:3306/mydb";

    private static final String user = "root";

    private static final String password = "";

    public static Connection getConnection() {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(jdbcURL, user, password);
            return connection;
        } catch (ClassNotFoundException e) {
            throw new DBConnectionFailException("Cannot find a driver");
        } catch (SQLException e) {
            throw new DBConnectionFailException("Cannot connect to Database");
        }
    }

    public static String blobToString(Blob blobText) {
        try {
            InputStream textStream = blobText.getBinaryStream();
            InputStreamReader streamReader = new InputStreamReader(textStream);
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder reviewText = new StringBuilder();
            while (true) {
                String s = reader.readLine();
                if (s == null) break;
                reviewText.append(s);
            }
            return reviewText.toString();
        } catch (IOException | SQLException e) {
            throw new BlobConvertException();
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionCloseException();
        }
    }

    public static InputStream stringToInputStream(String text) {
        if (text == null) {
            return new ByteArrayInputStream("".getBytes());
        }
        return new ByteArrayInputStream(text.getBytes());
    }
}

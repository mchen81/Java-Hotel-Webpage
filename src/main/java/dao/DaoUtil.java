package dao;

import com.google.gson.stream.JsonReader;
import exceptions.BlobConvertException;
import exceptions.ConnectionCloseException;
import exceptions.DBConnectionFailException;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoUtil extends FinalProjectDao {

    private static DataSource dataSource = new DataSource();

    public static Connection getConnection() {
        try {
            Class.forName(dataSource.driver);
            Connection connection = DriverManager.getConnection(dataSource.jdbcURL, dataSource.user, dataSource.password);
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

    private static class DataSource {

        private String driver;

        private String jdbcURL;

        private String user;

        private String password;

        public DataSource() {
            loadDBConfig();
        }

        private void loadDBConfig() {
            try {
                JsonReader jsonReader = new JsonReader(new FileReader("input/DatabaseConfig.json"));
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String jsonName = jsonReader.nextName();
                    switch (jsonName) {
                        case "driver":
                            driver = jsonReader.nextString();
                            break;
                        case "jdbcURL":
                            jdbcURL = jsonReader.nextString();
                            break;
                        case "user":
                            user = jsonReader.nextString();
                            break;
                        case "password":
                            password = jsonReader.nextString();
                            break;
                        default:
                            jsonReader.skipValue();
                            break;
                    }
                }
            } catch (IOException e) {
                throw new DBConnectionFailException("Cannot load DB config file");
            }
        }
    }
}

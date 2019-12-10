package dao;

import com.google.gson.stream.JsonReader;
import exceptions.DBConnectionFailException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoUtil {

    /** JDBC's basic info*/
    private static DataSource dataSource = new DataSource();

    /**
     * generate JDBC connection
     * @return a Connection
     */
    public static Connection getConnection() {
        try {
            Class.forName(dataSource.driver);
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection connection = DriverManager.getConnection(dataSource.jdbcURL, dataSource.user, dataSource.password);
            return connection;
        } catch (ClassNotFoundException e) {
            throw new DBConnectionFailException("Cannot find a driver");
        } catch (SQLException e) {
            throw new DBConnectionFailException("Cannot connect to Database");
        }
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

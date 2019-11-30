package dao.interfaces;

import dao.DaoUtil;
import exceptions.BlobConvertException;
import exceptions.ConnectionCloseException;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class FinalProjectDao {

    protected Connection getConnection() {
        return DaoUtil.getConnection();
    }

    protected String blobToString(Blob blobText) {
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

    protected void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionCloseException();
        }
    }

    protected InputStream stringToInputStream(String text) {
        if (text == null) {
            return new ByteArrayInputStream("".getBytes());
        }
        return new ByteArrayInputStream(text.getBytes());
    }
}

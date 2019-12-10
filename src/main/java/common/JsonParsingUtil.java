package common;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import common.bean.HotelJsonObject;
import common.bean.ReviewJsonObject;
import common.parser.ReviewParser;
import dao.DaoUtil;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class help parse input data to Database
 */
public class JsonParsingUtil {

    public static List<HotelJsonObject> parseHotelJsonFile(String filename) {
        filename = "input/hotels.json";
        Gson gson = new Gson();
        List<HotelJsonObject> hotels = new ArrayList<>();
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(filename));
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String basicInfo = jsonReader.nextName();
                if (basicInfo.equals("sr")) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        hotels.add(gson.fromJson(jsonReader, HotelJsonObject.class));
                    }
                    jsonReader.endArray();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
        } catch (IOException e) {
            System.out.println("Cannot find files");
        }
        return hotels;
    }

    public static List<ReviewJsonObject> parseReviewJsonFiles(String fileName) {
        ReviewParser reviewParser = new ReviewParser();
        try {
            reviewParser.parse("input/reviews");
            return new ArrayList<>(reviewParser.getReviews());
        } catch (IOException e) {
            System.out.println("cannot open files");
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        hotelsIntoDb();
        //updateHotelDB();
        reviewsToDB();
    }

    public static void hotelsIntoDb() throws Exception {
        Connection con = DaoUtil.getConnection();
        CallableStatement callableStatement = con.prepareCall("{Call final_project.insertHotels(?,?,?,?,?,?,?,?)}");
        for (HotelJsonObject hotel : parseHotelJsonFile("")) {
            callableStatement.setInt(1, hotel.getId());
            callableStatement.setString(2, hotel.getName());
            callableStatement.setString(3, hotel.getCountry());
            callableStatement.setString(4, hotel.getState());
            callableStatement.setString(5, hotel.getCity());
            callableStatement.setString(6, hotel.getAddress());
            callableStatement.setDouble(7, hotel.getLatitude());
            callableStatement.setDouble(8, hotel.getLongitude());
            callableStatement.addBatch();
        }
        callableStatement.executeBatch();
    }

    public static void updateHotelDB() throws Exception {
        Connection connection = DaoUtil.getConnection();
        CallableStatement callableStatement = connection.prepareCall("{Call updateHotel(?,?,?)}");
        for (HotelJsonObject hotel : parseHotelJsonFile("")) {
            callableStatement.setInt(1, hotel.getId());
            callableStatement.setDouble(2, hotel.getLatitude());
            callableStatement.setDouble(3, hotel.getLongitude());
            callableStatement.addBatch();
        }
        callableStatement.executeBatch();
    }

    public static void reviewsToDB() throws Exception {
        Set<String> keySet = new HashSet<>();
        Connection con = DaoUtil.getConnection();
        CallableStatement callableStatement = con.prepareCall("{Call insertReviews(?,?,?,?,?,?,?)}");
        for (ReviewJsonObject review : parseReviewJsonFiles("")) {
            if (keySet.contains(review.getReviewId()) || review.getReviewId() == null) {
                System.out.println(review.getReviewId());
                continue;
            } else {
                keySet.add(review.getReviewId());
            }
            callableStatement.setString(1, review.getReviewId());
            callableStatement.setString(2, review.getHotelId());
            callableStatement.setInt(3, review.getRatingOverall());
            callableStatement.setString(4, review.getTitle());
            // convert review text to byte as input steam
            InputStream reviewTextBlob = new ByteArrayInputStream(null == review.getReviewText() ? "".getBytes() : review.getReviewText().getBytes());
            callableStatement.setBlob(5, reviewTextBlob);
            callableStatement.setString(6, review.getUserNickname());
            try {
                callableStatement.setTimestamp(7, Timestamp.valueOf(review.getSubmissionTime()));
            } catch (Exception e) {
                continue;
            }
            callableStatement.addBatch();
        }
        callableStatement.executeBatch();
    }
}

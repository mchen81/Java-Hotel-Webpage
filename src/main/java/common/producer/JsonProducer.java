package common.producer;

import com.google.gson.Gson;
import dao.bean.Hotel;
import dao.bean.Review;

import java.util.List;

/**
 * This class if for helping producing json string
 */
public class JsonProducer {

    private static Gson gson = new Gson();

    /**
     * given a hotel in for, out put the json of this hotel
     *
     * @param hotel
     * @return
     */
    public static String produceHotelJson(Hotel hotel) {
        return gson.toJson(hotel);
    }

    /**
     * geven a list of hotels, output these hotels in a  json array
     *
     * @param hotels
     * @return
     */
    public static String produceHotelJson(List<Hotel> hotels) {
        return gson.toJson(hotels.toArray());
    }

    /**
     * geven a list of reviews, out put these hotels
     * @param reviews
     * @return
     */
    public static String produceReviewsJson(List<Review> reviews) {
        return gson.toJson(reviews.toArray());
    }
}

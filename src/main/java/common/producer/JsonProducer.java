package common.producer;

import com.google.gson.Gson;
import dao.bean.Hotel;
import dao.bean.Review;

import java.util.List;

public class JsonProducer {

    private static Gson gson = new Gson();

    public static String produceHotelJson(Hotel hotel) {
        return gson.toJson(hotel);
    }

    public static String produceHotelJson(List<Hotel> hotels) {
        return gson.toJson(hotels.toArray());
    }

    public static String produceReviewsJson(List<Review> reviews) {
        return gson.toJson(reviews.toArray());
    }
}

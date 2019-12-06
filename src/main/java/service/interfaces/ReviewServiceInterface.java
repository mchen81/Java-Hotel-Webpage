package service.interfaces;

import dao.bean.Review;

import java.util.List;

public interface ReviewServiceInterface {

    List<Review> findReviewsByHotelId(String hotelId);

    List<Review> findReviewsByUserId(String userId);

    /**
     * @param review Review Object
     * @return new review's Id
     */
    String addReview(Review review);

    void editReview(Review review);

    void removeReview(String reviewId);

    double getHotelAvgRating(String hotelId);
}

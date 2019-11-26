package service;

import common.bean.ReviewJsonObject;

import java.util.List;

public interface ReviewServiceInterface {

    List<ReviewJsonObject> findReviewsByHotelId(String hotelId);

    List<ReviewJsonObject> findReviewsByUserId(String userId);

    /**
     * @param review Review Object
     * @return new review's Id
     */
    String addReview(ReviewJsonObject review);

    void editReview(ReviewJsonObject review);

    void removeReview(String reviewId);
}

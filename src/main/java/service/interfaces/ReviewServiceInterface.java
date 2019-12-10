package service.interfaces;

import dao.bean.Review;
import service.ReviewService;

import java.util.List;

public interface ReviewServiceInterface {

    /**
     * find reviews in a hotel
     * @param hotelId hotel's id
     * @return a list of reviews
     */
    List<Review> findReviewsByHotelId(String hotelId);

    /**
     * find review written by an user
     * @param userId the user's id
     * @return a list od review
     */
    List<Review> findReviewsByUserId(String userId);

    /**
     * add a new review
     * @param review Review Object
     * @return new review's Id
     */
    String addReview(Review review);

    /**
     * edit an existing review, only title, rating, text can be modified
     * @param review edited review object
     */
    void editReview(Review review);

    /**
     * remove a review by it's id
     * @param reviewId the review id
     */
    void removeReview(String reviewId);

    /**
     * get rating info in a hotel
     * @param hotelId the hotel id
     * @return the Rating object can procide avg rating and number of rating
     */
    ReviewService.Rating getHotelRatingInfo(String hotelId);
}

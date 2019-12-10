package dao.interfaces;

import dao.bean.Review;

import java.util.List;

public interface ReviewDaoInterface {

    /**
     * get all reviews from db
     * @return
     */
    List<Review> getAllReviews();

    /**
     * get review by its id
     * @param reviewId
     * @return
     */
    Review getReviewById(String reviewId);

    /**
     * @param review the new review to db
     */
    void addReview(Review review);

    /**
     * @param review modify the review in db
     */
    void modifyReview(Review review);

    /**
     * delete the review in db
     * @param reviewId
     */
    void deleteReview(String reviewId);

    /**
     * get current the number of reviews in db
     * @return
     */
    int getReviewCount();

}

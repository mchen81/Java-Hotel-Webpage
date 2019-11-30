package dao.interfaces;

import dao.bean.Review;

import java.util.List;

public interface ReviewDaoInterface {

    List<Review> getAllReviews();

    void addReview(Review review);

    void modifyReview(Review review);

    void deleteReview(String reviewId);
}
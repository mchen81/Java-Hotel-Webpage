package dao;

import dao.bean.Review;
import dao.interfaces.ReviewDaoInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Test must be executed in order : getAllReviewsTest -> addReviewTest -> modifyReviewTest ->deleteReviewTest
 */
public class ReviewDaoTester {

    private ReviewDaoInterface reviewDao;

    private int currentReviewCount;

    @Before
    public void setUp() {
        reviewDao = new ReviewDao();
        currentReviewCount = reviewDao.getReviewCount();
    }

    @Test
    public void getAllReviewsTest() {
        List<Review> reviews = reviewDao.getAllReviews();
        Assert.assertEquals(currentReviewCount, reviews.size());
    }

    @Test
    public void addReviewTest() {
        reviewDao.deleteReview("111111");
        Review review = new Review();
        review.setReviewId("111111");
        review.setHotelId("360");
        review.setUserId(1);
        review.setRatingOverall(5);
        review.setTitle("For Test");
        review.setReviewText("This is the review text for test");
        review.setSubmissionTime(Timestamp.valueOf(LocalDateTime.now()));
        reviewDao.addReview(review);
        Assert.assertEquals(currentReviewCount + 1, reviewDao.getReviewCount());
    }

    @Test
    public void modifyReviewTest() {
        String reviewId = "111111";
        int rating = 0;
        String title = "I am Modified Text";
        String text = "This review has been modified";
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        //
        Review review = new Review();
        review.setReviewId(reviewId);
        review.setRatingOverall(rating);
        review.setTitle(title);
        review.setReviewText(text);
        review.setSubmissionTime(timestamp);
        reviewDao.modifyReview(review);
        //
        review = reviewDao.getReviewById(reviewId);
        Assert.assertEquals(rating, review.getRatingOverall());
        Assert.assertEquals(title, review.getTitle());
    }

    @Test
    public void deleteReviewTest() {
        reviewDao.deleteReview("111111");
        Assert.assertEquals(currentReviewCount - 1, reviewDao.getReviewCount());
    }
}

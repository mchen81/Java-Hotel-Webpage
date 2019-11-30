package service;

import dao.bean.Review;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.interfaces.ReviewServiceInterface;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewServiceTest {

    private ReviewServiceInterface reviewService;

    private static String reviewId;

    @Before
    public void setup() {
        reviewService = ServicesSingleton.getReviewService();
    }

    @Test
    public void findReviewsByHotelIdTest() {
        List<Review> reviews = reviewService.findReviewsByHotelId("360");
        Assert.assertEquals(15, reviews.size());
    }

    @Test
    public void findReviewsByUserIdTest() {
        List<Review> reviews = reviewService.findReviewsByUserId("1");
        Assert.assertEquals(1221, reviews.size());
    }

    @Test
    public void addReviewTest(){
        Review review = new Review();
        review.setHotelId("360");
        review.setUserId(1);
        review.setRatingOverall(5);
        review.setTitle("For Test");
        review.setReviewText("This is the review text for test");
        review.setSubmissionTime(Timestamp.valueOf(LocalDateTime.now()));
        reviewId = reviewService.addReview(review);
        Assert.assertNotNull(reviewId);
        System.out.println(reviewId);
    }

}

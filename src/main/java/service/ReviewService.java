package service;

import common.RandomNumberUtil;
import dao.ReviewDao;
import dao.bean.Review;
import dao.interfaces.ReviewDaoInterface;
import service.interfaces.ReviewServiceInterface;

import java.security.SecureRandom;
import java.util.*;

public class ReviewService implements ReviewServiceInterface {

    private ReviewDaoInterface reviewDao;

    /**
     * Key: hotel Id, value: the review list under the hotel, order by submission time
     */
    private Map<String, TreeSet<Review>> reviewsMapCache;

    /**
     * key: review id, values are all reviews
     */
    private Map<String, Review> reviewsCache;


    private Map<String, Rating> hotelsAvgRatingMap;


    public ReviewService() {
        reviewDao = new ReviewDao();
        reviewsMapCache = new HashMap<>();
        reviewsCache = new HashMap<>();
        hotelsAvgRatingMap = new HashMap<>();
        loadReviewsInfo();
    }

    private void loadReviewsInfo() {
        List<Review> reviews = reviewDao.getAllReviews();
        for (Review review : reviews) {
            if (reviewsMapCache.containsKey(review.getHotelId())) {
                reviewsMapCache.get(review.getHotelId()).add(review);
                hotelsAvgRatingMap.get(review.getHotelId()).add(review.getRatingOverall());
            } else {
                List<Review> newList = new ArrayList<>();
                newList.add(review);
                reviewsMapCache.put(review.getHotelId(), new TreeSet<>(newList));
                Rating rating = new Rating(review.getRatingOverall());
                hotelsAvgRatingMap.put(review.getHotelId(), rating);

            }
            reviewsCache.put(review.getReviewId(), review);
        }
    }

    @Override
    public List<Review> findReviewsByHotelId(String hotelId) {
        return new ArrayList<>(reviewsMapCache.get(hotelId));
    }

    @Override
    public List<Review> findReviewsByUserId(String userId) {
        List<Review> reviews = new ArrayList<>();
        for (Review review : reviewsCache.values()) {
            if (userId.equals(String.valueOf(review.getUserId()))) {
                reviews.add(review);
            }
        }
        return reviews;
    }

    @Override
    public String addReview(Review review) {
        String reviewId = generateNewReviewId();
        review.setReviewId(reviewId);
        reviewDao.addReview(review);
        reviewsCache.put(reviewId, review);
        reviewsMapCache.get(review.getHotelId()).add(review);
        hotelsAvgRatingMap.get(review.getHotelId()).add(review.getRatingOverall());
        return reviewId;
    }

    @Override
    public void editReview(Review review) {

        double originRating = reviewsCache.get(review.getReviewId()).getRatingOverall();
        reviewDao.modifyReview(review);
        reviewsCache.replace(review.getReviewId(), review);
        reviewsMapCache.get(review.getHotelId()).removeIf(r -> r.equals(review));
        reviewsMapCache.get(review.getHotelId()).add(review);
        hotelsAvgRatingMap.get(review.getHotelId()).modify(originRating, review.getRatingOverall());
    }

    @Override
    public void removeReview(String reviewId) {
        reviewDao.deleteReview(reviewId);
        Review removedReview = reviewsCache.get(reviewId);
        reviewsCache.remove(removedReview);
        reviewsMapCache.get(removedReview.getHotelId()).removeIf(r -> r.equals(removedReview));
        hotelsAvgRatingMap.get(removedReview.getHotelId()).delete(removedReview.getRatingOverall());
    }

    private String generateNewReviewId() {
        String reviewId = RandomNumberUtil.generateRandomString(24);
        if (reviewsCache.keySet().contains(reviewId.toString())) {
            return generateNewReviewId();
        }
        return reviewId.toString();
    }

    private static class Rating {
        private double number;
        private double sum;

        public Rating(double firstRating) {
            number = 1;
            sum = firstRating;
        }

        private double getAvgRating() {
            return sum / number;
        }

        private void add(double rating) {
            sum += rating;
            number++;
        }

        private void delete(double rating) {
            sum -= rating;
            number--;
        }

        private void modify(double origin, double newRating) {
            sum = sum - origin + newRating;
        }

    }
}

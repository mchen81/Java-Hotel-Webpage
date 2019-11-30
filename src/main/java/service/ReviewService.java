package service;

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

    private Map<String, Review> reviewsCache;
    /**
     * All review id
     */
    private Set<String> reviewIdSet;

    /**
     * for generate random review id
     */
    private static SecureRandom random = new SecureRandom();

    public ReviewService() {
        reviewDao = new ReviewDao();
        reviewsMapCache = new HashMap<>();
        reviewsCache = new HashMap<>();
        reviewIdSet = new HashSet<>();
        loadReviewsInfo();
    }

    private void loadReviewsInfo() {
        List<Review> reviews = reviewDao.getAllReviews();
        for (Review review : reviews) {
            if (reviewsMapCache.containsKey(review.getHotelId())) {
                reviewsMapCache.get(review.getHotelId()).add(review);
            } else {
                List<Review> newList = new ArrayList<>();
                newList.add(review);
                reviewsMapCache.put(review.getHotelId(), new TreeSet<>(newList));
            }
            reviewIdSet.add(review.getReviewId());
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
        reviewIdSet.add(reviewId);
        reviewsCache.put(reviewId, review);
        reviewsMapCache.get(review.getHotelId()).add(review);
        return reviewId;
    }

    @Override
    public void editReview(Review review) {
        reviewDao.modifyReview(review);
        removeReview(review.getReviewId());
        reviewDao.addReview(review);
        reviewIdSet.add(review.getReviewId());
        reviewsCache.put(review.getReviewId(), review);
        reviewsMapCache.get(review.getHotelId()).add(review);
        addReview(review);
    }

    @Override
    public void removeReview(String reviewId) {
        reviewDao.deleteReview(reviewId);
        Review removedReview = reviewsCache.get(reviewId);
        reviewsCache.remove(removedReview);
        reviewsMapCache.get(removedReview.getHotelId()).removeIf(r -> r.equals(removedReview));
        reviewIdSet.remove(reviewId);
    }

    private String generateNewReviewId() {
        StringBuilder reviewId = new StringBuilder();
        String text = "1234567890qwertyuiopasdfghjklzxcvbnm";
        for (int i = 0; i < 24; i++) {
            reviewId.append(text.charAt(random.nextInt(36)));
        }
        if (reviewIdSet.contains(reviewId.toString())) {
            return generateNewReviewId();
        }
        return reviewId.toString();
    }
}

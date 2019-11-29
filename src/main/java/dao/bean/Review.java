package dao.bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Review implements Comparable<Review> {

    private String hotelId;

    private String reviewId;

    private int ratingOverall;

    private String title;

    private String reviewText;

    private long userId;

    private Timestamp submissionTime;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public int getRatingOverall() {
        return ratingOverall;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Timestamp submissionTime) {
        this.submissionTime = submissionTime;
    }

    private LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setRatingOverall(int ratingOverall) {
        this.ratingOverall = ratingOverall;
    }

    @Override
    public int hashCode() {
        StringBuilder hashCode = new StringBuilder();
        for (char c : reviewId.toCharArray()) {
            hashCode.append((int) c);
        }
        return Integer.parseInt(hashCode.toString());
    }

    @Override
    public int compareTo(Review o) {
        return submissionTime.compareTo(o.getSubmissionTime());
    }
}

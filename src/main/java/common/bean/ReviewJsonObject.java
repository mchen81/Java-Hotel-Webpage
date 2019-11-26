package common.bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReviewJsonObject {

    private String hotelId;

    private String reviewId;

    private int ratingOverall;

    private String title;

    private String reviewText;

    private String userNickname = "Anonymous";

    private LocalDateTime submissionTime;

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

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        if (userNickname.isEmpty()) {
            this.userNickname = "Anonymous";
        } else {
            this.userNickname = userNickname;
        }
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = this.parseDateTime(submissionTime);
    }

    private LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    public void setRatingOverall(int ratingOverall) {
        this.ratingOverall = ratingOverall;
    }
}


package dao;

import dao.bean.Review;
import dao.interfaces.ReviewDaoInterface;
import exceptions.QueryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao extends FinalProjectDao implements ReviewDaoInterface {

    public static void main(String[] args) {
        ReviewDao r = new ReviewDao();
        List<Review> reviews = r.getAllReviews();
        System.out.println(reviews.get(1022).getReviewText());
    }

    private static final String SQL_GET_ALL_REVIEWS = "SELECT * FROM FINAL_PROJECT.REVIEW";

    private static final String SQL_ADD_REVIEW = "INSERT INTO FINAL_PROJECT.REVIEW VALUES(?,?,?,?,?,?,?)";

    private static final String SQL_EDIT_REVIEW = "UPDATE FINAL_PROJECT.REVIEW " +
            "SET HOTEL_ID = ? , RANTING = ? , TITLE = ? , REVIEW_TEXT = ? + SUBMIT_TIME = ? " +
            "WHERE REVIEW_ID = ?";

    private Connection dbConnection;

    @Override
    public List<Review> getAllReviews() {
        dbConnection = DaoUtil.getConnection();
        List<Review> reviews = new ArrayList<>();
        try {
            PreparedStatement ps = dbConnection.prepareStatement(SQL_GET_ALL_REVIEWS);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                review.setReviewId(resultSet.getString(1));
                review.setHotelId(resultSet.getString(2));
                review.setRatingOverall(resultSet.getInt(3));
                review.setTitle(resultSet.getString(4));
                review.setReviewText(DaoUtil.blobToString(resultSet.getBlob(5)));
                review.setUserId(resultSet.getLong(6));
                review.setSubmissionTime(resultSet.getTimestamp(7));
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new QueryException();
        } finally {
            DaoUtil.closeConnection(dbConnection);
        }
        return reviews;
    }

    @Override
    public void addReview(Review review) {
        dbConnection = DaoUtil.getConnection();
        try {
            PreparedStatement ps = dbConnection.prepareStatement(SQL_ADD_REVIEW);
            ps.setString(1, review.getReviewId());
            ps.setString(2, review.getHotelId());
            ps.setInt(3, review.getRatingOverall());
            ps.setString(4, review.getTitle());
            ps.setBlob(5, DaoUtil.stringToInputStream(review.getReviewText()));
            ps.setLong(6, review.getUserId());
            ps.setTimestamp(7, review.getSubmissionTime());
            ps.execute();
        } catch (SQLException e) {
            throw new QueryException();
        } finally {
            DaoUtil.closeConnection(dbConnection);
        }
    }

    @Override
    public void modifyReview(Review review) {
    }

    @Override
    public void deleteReview(String reviewId) {
    }
}

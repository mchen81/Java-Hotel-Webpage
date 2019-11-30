package dao;

import dao.bean.Review;
import dao.interfaces.FinalProjectDao;
import dao.interfaces.ReviewDaoInterface;
import exceptions.QueryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao extends FinalProjectDao implements ReviewDaoInterface {

    private static final String SQL_GET_ALL_REVIEWS = "SELECT * FROM REVIEW";

    private static final String SQL_ADD_REVIEW = "INSERT INTO REVIEW VALUES(?,?,?,?,?,?,?)";

    private static final String SQL_EDIT_REVIEW = "UPDATE REVIEW " +
            "SET HOTEL_ID = ? , RANTING = ? , TITLE = ? , REVIEW_TEXT = ? + SUBMIT_TIME = ? " +
            "WHERE REVIEW_ID = ?";

    @Override
    public List<Review> getAllReviews() {
        Connection dbConnection = getConnection();
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
                review.setReviewText(blobToString(resultSet.getBlob(5)));
                review.setUserId(resultSet.getLong(6));
                review.setSubmissionTime(resultSet.getTimestamp(7));
                reviews.add(review);
            }
        } catch (SQLException e) {
            throw new QueryException();
        } finally {
            closeConnection(dbConnection);
        }
        return reviews;
    }

    @Override
    public void addReview(Review review) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_ADD_REVIEW);
            ps.setString(1, review.getReviewId());
            ps.setString(2, review.getHotelId());
            ps.setInt(3, review.getRatingOverall());
            ps.setString(4, review.getTitle());
            ps.setBlob(5, stringToInputStream(review.getReviewText()));
            ps.setLong(6, review.getUserId());
            ps.setTimestamp(7, review.getSubmissionTime());
            ps.execute();
        } catch (SQLException e) {
            throw new QueryException();
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void modifyReview(Review review) {
    }

    @Override
    public void deleteReview(String reviewId) {
    }
}

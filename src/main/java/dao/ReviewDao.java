package dao;

import dao.bean.Review;
import dao.interfaces.FinalProjectDao;
import dao.interfaces.ReviewDaoInterface;
import exceptions.QueryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao extends FinalProjectDao implements ReviewDaoInterface {

    private static final String CALL_GET_ALL_REVIEW = "{CALL getAllReviews()}";

    private static final String CALL_GET_REVIEW_BY_ID = "{CALL getReviewById(?)}";

    private static final String CALL_ADD_REVIEW = "{CALL addReview(?,?,?,?,?,?,?)}";

    private static final String CALL_UPDATE_REVIEW = "{CALL updateReview(?,?,?,?,?)}";

    private static final String CALL_DELETE_REVIEW = "{CALL deleteReview(?)}";

    private static final String QUERY_GET_COUNT = "SELECT COUNT(*) FROM mydb.REVIEW";

    @Override
    public List<Review> getAllReviews() {
        Connection connection = getConnection();
        List<Review> reviews = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_GET_ALL_REVIEW);
            ResultSet resultSet = callableStatement.executeQuery();
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
            throw new QueryException(e.getErrorCode());
        } finally {
            closeConnection(connection);
        }
        return reviews;
    }

    @Override
    public Review getReviewById(String reviewId) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_GET_REVIEW_BY_ID);
            callableStatement.setString(1, reviewId);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                Review review = new Review();
                review.setReviewId(resultSet.getString(1));
                review.setHotelId(resultSet.getString(2));
                review.setRatingOverall(resultSet.getInt(3));
                review.setTitle(resultSet.getString(4));
                review.setReviewText(blobToString(resultSet.getBlob(5)));
                review.setUserId(resultSet.getLong(6));
                review.setSubmissionTime(resultSet.getTimestamp(7));
                return review;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void addReview(Review review) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_ADD_REVIEW);
            callableStatement.setString(1, review.getReviewId());
            callableStatement.setString(2, review.getHotelId());
            callableStatement.setFloat(3, review.getRatingOverall());
            callableStatement.setString(4, review.getTitle());
            callableStatement.setBlob(5, stringToInputStream(review.getReviewText()));
            callableStatement.setLong(6, review.getUserId());
            callableStatement.setTimestamp(7, review.getSubmissionTime());
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new QueryException(e.getErrorCode());
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void modifyReview(Review review) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_UPDATE_REVIEW);
            callableStatement.setString(1, review.getReviewId());
            callableStatement.setFloat(2, review.getRatingOverall());
            callableStatement.setString(3, review.getTitle());
            callableStatement.setBlob(4, stringToInputStream(review.getReviewText()));
            callableStatement.setTimestamp(5, review.getSubmissionTime());
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new QueryException(e.getErrorCode());
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void deleteReview(String reviewId) {
        Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall(CALL_DELETE_REVIEW);
            callableStatement.setString(1, reviewId);
            callableStatement.execute();
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public int getReviewCount() {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_GET_COUNT);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new QueryException(e.getErrorCode());
        } finally {
            closeConnection(connection);
        }
    }
}

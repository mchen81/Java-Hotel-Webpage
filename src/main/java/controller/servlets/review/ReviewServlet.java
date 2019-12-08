package controller.servlets.review;

import com.google.gson.stream.JsonWriter;
import common.producer.JsonProducer;
import controller.servlets.MyHttpServlet;
import dao.bean.Review;
import service.ServicesSingleton;
import service.interfaces.ReviewServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ReviewServlet extends MyHttpServlet {

    private ReviewServiceInterface reviewService;

    public ReviewServlet() {
        reviewService = ServicesSingleton.getReviewService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hotelId = request.getParameter("hotelId");
        String userId = request.getParameter("userId");
        setJsonResponse(response);
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());
        jsonWriter.beginObject();
        if (hotelId != null) {
            List<Review> reviews = reviewService.findReviewsByHotelId(hotelId);
            if (reviews == null || reviews.size() == 0) {
                jsonWriter.name("success").value(false);
                jsonWriter.name("message").value("No reviews found");
            } else {
                jsonWriter.name("success").value(true);
                jsonWriter.name("reviews").value(JsonProducer.produceReviewsJson(reviews));
            }
        } else if (userId != null) {
            List<Review> reviews = reviewService.findReviewsByUserId(userId);
            if (reviews == null || reviews.size() == 0) {
                jsonWriter.name("success").value(false);
                jsonWriter.name("message").value("No reviews found");
            } else {
                jsonWriter.name("success").value(true);
                jsonWriter.name("reviews").value(JsonProducer.produceReviewsJson(reviews));
            }
        } else {
            jsonWriter.name("success").value(false);
            jsonWriter.name("message").value("No reviews found");
        }
        jsonWriter.endObject();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hotelId = request.getParameter("hotelId");
        String title = request.getParameter("title");
        String reviewText = request.getParameter("reviewText");
        String rate = request.getParameter("rate");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId == null || userId.isBlank()) {
            userId = "1";
        }
        Review review = new Review();
        review.setHotelId(hotelId);
        review.setRatingOverall(Integer.parseInt(rate));
        review.setReviewText(reviewText);
        review.setTitle(title);
        review.setUserId(Integer.parseInt(userId));
        reviewService.addReview(review);


        // redirect to hotel
    }
}

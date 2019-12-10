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
import java.util.Map;

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

    @Override  // add new review
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hotelId = request.getParameter("hotelId");
        String title = request.getParameter("title");
        String reviewText = request.getParameter("reviewText");
        String rate = request.getParameter("rate");
        if (hotelId == null || title == null || reviewText == null || rate == null || title.isEmpty() || reviewText.isEmpty()) {
            setReturnHtml("HotelDetail");
            String script = "<script>alert('You must write both title and review'); window.location.replace('/hotelDetail?hotelId=" + hotelId + "'); </script>";
            addAttribute("script", script);
            outPutHtml(response);
            return;
        }
        setJsonResponse(response);
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null || userId.toString().isEmpty()) {
            userId = 1L;
        }
        Review review = new Review();
        review.setHotelId(hotelId);
        review.setRatingOverall(Float.parseFloat(rate));
        review.setReviewText(reviewText);
        review.setTitle(title);
        review.setUserId(userId);
        reviewService.addReview(review);
        initVelocityEngine(request);
        setBasicHtmlResponse(response);
        setReturnHtml("HotelDetail");
        String script = "<script>alert('Successfully Added Review'); window.location.replace('/hotelDetail?hotelId=" + hotelId + "'); </script>";
        addAttribute("script", script);
        addAttribute("isLoggedIn", isLoggedIn(request));
        outPutHtml(response);
    }

    @Override // update a review
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> parameterMap = getAjaxRequestParameterMap(request.getReader());
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());
        setJsonResponse(response);
        String reviewId = parameterMap.get("reviewId");
        String title = parameterMap.get("title");
        String reviewText = parameterMap.get("reviewText");
        if (reviewId == null || title == null || title.isEmpty() || reviewText == null || reviewText.isEmpty()) {
            jsonWriter.beginObject().name("success").value(false).endObject();
            return;
        }
        Review review = new Review();
        review.setReviewId(reviewId);
        review.setTitle(title);
        review.setReviewText(reviewText);
        reviewService.editReview(review);
        jsonWriter.beginObject().name("success").value(true).endObject();
    }

    @Override //delete a review
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> parameterMap = getAjaxRequestParameterMap(request.getReader());
        String reviewId = parameterMap.get("reviewId");
        setJsonResponse(response);
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());
        jsonWriter.beginObject();
        if (reviewId == null) {
            jsonWriter.name("success").value(false).endObject();
        } else {
            reviewService.removeReview(reviewId);
            jsonWriter.name("success").value(true).endObject();
        }
    }
}

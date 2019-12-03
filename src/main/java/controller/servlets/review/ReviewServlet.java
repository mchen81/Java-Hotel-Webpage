package controller.servlets.review;

import common.producer.JsonProducer;
import dao.bean.Review;
import service.ServicesSingleton;
import service.interfaces.ReviewServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReviewServlet extends HttpServlet {

    private ReviewServiceInterface reviewService;

    public ReviewServlet() {
        reviewService = ServicesSingleton.getReviewService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doGet(req, resp);
        String hotelId = request.getParameter("hotelId");
        List<Review> reviews = reviewService.findReviewsByHotelId(hotelId);
        JsonProducer.produceReviewsJson(reviews);
    }
}

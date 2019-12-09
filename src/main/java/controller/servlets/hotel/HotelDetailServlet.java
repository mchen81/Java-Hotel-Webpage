package controller.servlets.hotel;

import controller.servlets.MyHttpServlet;
import dao.bean.Hotel;
import dao.bean.Review;
import service.ServicesSingleton;
import service.interfaces.HotelServiceInterface;
import service.interfaces.ReviewServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class HotelDetailServlet extends MyHttpServlet {

    private HotelServiceInterface hotelService = ServicesSingleton.getHotelService();
    private ReviewServiceInterface reviewService = ServicesSingleton.getReviewService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initVelocityEngine(request);
        setBasicHtmlResponse(response);
        setReturnHtml("HotelDetail");
        addAttribute("isLoggedIn", isLoggedIn(request));
        String hotelId = request.getParameter("hotelId");
        if (hotelId == null || hotelId.isBlank()) {
            addAttribute("script", "<script>alert('No Hotel Detail Found')</script>");
        }

        Hotel hotel = hotelService.findHotelById(hotelId);
        List<Review> reviews = reviewService.findReviewsByHotelId(hotel.getId());
        addAttribute("hotel", hotel);
        addAttribute("reviews", reviews);
        outPutHtml(response);
    }
}

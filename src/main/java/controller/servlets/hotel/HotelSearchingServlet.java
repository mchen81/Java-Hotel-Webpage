package controller.servlets.hotel;

import com.google.gson.stream.JsonWriter;
import controller.servlets.MyHttpServlet;
import dao.bean.Hotel;
import service.ReviewService;
import service.ServicesSingleton;
import service.interfaces.HotelServiceInterface;
import service.interfaces.ReviewServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HotelSearchingServlet extends MyHttpServlet {

    private HotelServiceInterface hotelService;

    private ReviewServiceInterface reviewService;

    public HotelSearchingServlet() {
        hotelService = ServicesSingleton.getHotelService();
        reviewService = ServicesSingleton.getReviewService();
    }

    /**
     * Search Hotel, and show hotels matched the keywords
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initVelocityEngine(request);
        setBasicHtmlResponse(response);
        setReturnHtml("ShowHotels");
        addAttribute("isLoggedIn", isLoggedIn(request));
        String cityKeyword = request.getParameter("city");
        String nameKeyword = request.getParameter("hotelName");
        List<Hotel> foundHotels = hotelService.findHotelsByKeyWords(cityKeyword, nameKeyword);
        addAttribute("isLoggedIn", isLoggedIn(request));
        if (foundHotels == null || foundHotels.size() == 0) {
            addAttribute("script", "<script>alert('Sorry, we do not find any matching hotels.'); window.location.replace(\"/home\");</script>");
            outPutHtml(response);
            return;
        }
        for (Hotel hotel : foundHotels) {
            ReviewService.Rating rating = reviewService.getHotelRatingInfo(hotel.getId());
            if (rating == null) {
                hotel.setRating(-1D);
            } else {
                hotel.setRating(rating.getAvgRating());
            }
        }
        Collections.sort(foundHotels, (o1, o2) -> {
            if (o1.getRating() != null && o2.getRating() == null) {
                return -1;
            } else if (o1.getRating() == null && o2.getRating() != null) {
                return 1;
            } else if (o1.getRating() == null && o2.getRating() == null) {
                return 0;
            }
            return o1.getRating().compareTo(o2.getRating()) * -1;
        });
        addAttribute("hotels", foundHotels);
        outPutHtml(response);
    }
}

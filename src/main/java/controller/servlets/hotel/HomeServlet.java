package controller.servlets.hotel;

import controller.servlets.MyHttpServlet;
import dao.bean.Hotel;
import service.HotelService;
import service.ServicesSingleton;
import service.interfaces.HotelServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public class HomeServlet extends MyHttpServlet {

    private HotelServiceInterface hotelService;

    public HomeServlet() {
        hotelService = ServicesSingleton.getHotelService();
    }

    /**
     * home page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initVelocityEngine(request);
        setBasicHtmlResponse(response);
        setReturnHtml("HomePage");
        List<Hotel> hotels = hotelService.findAllHotels();
        TreeSet<String> cities = new TreeSet<>();
        for (Hotel hotel : hotels) {
            cities.add(hotel.getCity());
        }
        addAttribute("cities", cities);
        addAttribute("isLoggedIn", isLoggedIn(request));
        addAttribute("hotels", hotels);
        outPutHtml(response);
    }
}

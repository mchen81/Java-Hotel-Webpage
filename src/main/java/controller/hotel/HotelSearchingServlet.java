package controller.hotel;

import dao.bean.Hotel;
import service.ServicesSingleton;
import service.interfaces.HotelServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class HotelSearchingServlet extends HttpServlet {

    private HotelServiceInterface hotelService;

    public HotelSearchingServlet() {
        hotelService = ServicesSingleton.getHotelService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cityKeyword = request.getParameter("city");
        String nameKeyword = request.getParameter("name");
        List<Hotel> foundHotels = hotelService.findHotelsByKeyWords(cityKeyword, nameKeyword);
        // TODO return these hotels
    }
}

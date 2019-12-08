package controller.servlets.hotel;

import com.google.gson.stream.JsonWriter;
import controller.servlets.MyHttpServlet;
import dao.bean.Hotel;
import service.ServicesSingleton;
import service.interfaces.HotelServiceInterface;
import service.interfaces.ReviewServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class HotelSearchingServlet extends MyHttpServlet {

    private HotelServiceInterface hotelService;

    private ReviewServiceInterface reviewService;

    public HotelSearchingServlet() {
        hotelService = ServicesSingleton.getHotelService();
        reviewService = ServicesSingleton.getReviewService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        initVelocityEngine(request);
        setBasicHtmlResponse(response);
        setReturnHtml("ShowHotels");
        String cityKeyword = request.getParameter("city");
        String nameKeyword = request.getParameter("hotelName");
        List<Hotel> foundHotels = hotelService.findHotelsByKeyWords(cityKeyword, nameKeyword);
        for (Hotel hotel : foundHotels) {
            hotel.setRating(reviewService.getHotelAvgRating(hotel.getId()));
        }
        addAttribute("hotels", foundHotels);
        outPutHtml(response);

    }

    public void originGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // return json
        String cityKeyword = request.getParameter("city");
        String nameKeyword = request.getParameter("hotelName");

        List<Hotel> foundHotels = hotelService.findHotelsByKeyWords(cityKeyword, nameKeyword);
        setJsonResponse(response);
        PrintWriter out = response.getWriter();
        JsonWriter jsonWriter = new JsonWriter(out);
        if (foundHotels == null || foundHotels.size() == 0) {
            jsonWriter.beginObject();
            jsonWriter.name("success").value(false);
            jsonWriter.endObject();
        } else {
            jsonWriter.beginObject();
            jsonWriter.name("success").value(true);
            jsonWriter.name("hotels").beginArray();
            for (Hotel hotel : foundHotels) {
                jsonWriter.beginObject();
                jsonWriter.name("hotelId").value(hotel.getId());
                jsonWriter.name("name").value(hotel.getName());
                jsonWriter.name("addr").value(hotel.getAddress());
                jsonWriter.name("city").value(hotel.getCity());
                jsonWriter.name("state").value(hotel.getState());
                jsonWriter.name("lat").value(hotel.getLatitude().toString());
                jsonWriter.name("lng").value(hotel.getLongitude().toString());
                jsonWriter.endObject();
            }
            jsonWriter.endArray().endObject();
        }
        out.flush();
    }

}

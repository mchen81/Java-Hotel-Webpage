package controller.servlets.hotel;

import com.google.gson.stream.JsonWriter;
import controller.servlets.MyHttpServlet;
import service.ServicesSingleton;
import service.bean.TouristAttraction;
import service.interfaces.HotelServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TouristAttractionServlet extends MyHttpServlet {

    private HotelServiceInterface hotelService;

    public TouristAttractionServlet() {
        hotelService = ServicesSingleton.getHotelService();
    }

    /**
     * Return Json of attractions
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String hotelId = request.getParameter("hotelId");
        String radius = request.getParameter("radius");
        double miles = Double.parseDouble(radius);
        List<TouristAttraction> touristAttractions = hotelService.findTouristAttraction(hotelId, miles);
        setJsonResponse(response);
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());
        jsonWriter.beginObject();
        if (touristAttractions == null || touristAttractions.size() == 0) {
            jsonWriter.name("success").value(false);
            jsonWriter.name("message").value("No Attractions Found");
            jsonWriter.name("touristAttractions").value("");
        } else {
            jsonWriter.name("success").value(true);
            jsonWriter.name("message").value(String.format("%d Attractions Found", touristAttractions.size()));
            jsonWriter.name("touristAttractions");
            jsonWriter.beginArray();
            for (TouristAttraction touristAttraction : touristAttractions) {
                jsonWriter.beginObject();
                jsonWriter.name("name").value(touristAttraction.getName());
                jsonWriter.name("address").value(touristAttraction.getAddress());
                jsonWriter.name("rating").value(touristAttraction.getRating());
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();
    }
}

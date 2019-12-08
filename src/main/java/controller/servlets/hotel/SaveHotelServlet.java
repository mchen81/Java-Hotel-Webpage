package controller.servlets.hotel;

import com.google.gson.stream.JsonWriter;
import controller.servlets.MyHttpServlet;
import dao.bean.Hotel;
import exceptions.HotelHasBeenSavedException;
import exceptions.UserNameHasExistedException;
import org.eclipse.jetty.server.session.Session;
import service.ServicesSingleton;
import service.interfaces.HotelServiceInterface;
import service.interfaces.UserServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Set;

public class SaveHotelServlet extends MyHttpServlet {

    private UserServiceInterface userService;

    private HotelServiceInterface hotelService;

    public SaveHotelServlet() {
        this.userService = ServicesSingleton.getUserService();
        this.hotelService = ServicesSingleton.getHotelService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        Set<String> savedHotels = userService.getSavedHotels(userId);
        setJsonResponse(response);
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());
        jsonWriter.beginObject();
        if (savedHotels == null || savedHotels.size() == 0) {
            jsonWriter.name("success").value(false);
        } else {
            jsonWriter.name("success").value(true);
            jsonWriter.name("savedHotels");
            jsonWriter.beginArray();
            for (String hotelId : savedHotels) {
                Hotel hotel = hotelService.findHotelById(hotelId);
                jsonWriter.name("Id").value(hotelId);
                jsonWriter.name("name").value(hotel.getName());
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> parameterMap = getAjaxRequestParameterMap(request.getReader());
        String userId = (String) request.getSession().getAttribute("userId");
        String hotelId = parameterMap.get("hotelId");
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());
        jsonWriter.beginObject().name("success");
        try {
            userService.addSavedHotel(userId, hotelId);
            jsonWriter.value(true).endObject();
        } catch (HotelHasBeenSavedException e) {
            jsonWriter.value(false).endObject();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}

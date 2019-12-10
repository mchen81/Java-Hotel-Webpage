package controller.servlets.hotel;

import com.google.gson.stream.JsonWriter;
import controller.servlets.MyHttpServlet;
import dao.bean.Hotel;
import exceptions.HotelHasBeenSavedException;
import service.ServicesSingleton;
import service.interfaces.HotelServiceInterface;
import service.interfaces.UserServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class SaveHotelServlet extends MyHttpServlet {

    private UserServiceInterface userService;

    private HotelServiceInterface hotelService;

    public SaveHotelServlet() {
        this.userService = ServicesSingleton.getUserService();
        this.hotelService = ServicesSingleton.getHotelService();
    }

    /**
     * present user's saved hotels
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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
        Long userId = (Long) request.getSession().getAttribute("userId");
        setJsonResponse(response);
        String hotelId = parameterMap.get("hotelId");
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());
        jsonWriter.beginObject().name("success");
        if (userId == null || userId == 1L) {
            jsonWriter.value(false).name("message").value("Please Log in to save hotel").endObject();
        } else {
            try {
                userService.addSavedHotel(userId.toString(), hotelId);
                jsonWriter.value(true).name("message").value("Save Success").endObject();
            } catch (HotelHasBeenSavedException e) {
                jsonWriter.value(false).name("message").value("You have saved this hotel").endObject();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        Map<String, String> parameterMap = getAjaxRequestParameterMap(request.getReader());
        String hotelId = parameterMap.get("hotelId");
        System.out.println(userId);
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());
        setJsonResponse(response);
        if (userId != null && hotelId.isEmpty()) {
            userService.clearSavedHotel(userId.toString());
            jsonWriter.beginObject().name("success").value(true).endObject();
        } else if (userId != null && !hotelId.isEmpty()) {
            userService.removeOneSavedHotel(userId.toString(), hotelId);
            jsonWriter.beginObject().name("success").value(true).endObject();
        } else {
            jsonWriter.beginObject().name("success").value(false).endObject();
        }
    }
}

package controller.servlets.user;

import com.google.gson.stream.JsonWriter;
import controller.servlets.MyHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserProfileServlet extends MyHttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // return profile page when session > 1
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        setJsonResponse(response);
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());

        if (userId == null || userId <= 1) {
            jsonWriter.beginObject().name("success").value(false);
            jsonWriter.name("You must log in to see profile");
            jsonWriter.endObject();
        } else {
            jsonWriter.beginObject().name("success").value(true).endObject();
        }
    }
}

package controller.servlets.user;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import controller.servlets.MyHttpServlet;
import dao.bean.User;
import exceptions.UserDoesNotExistException;
import exceptions.WrongPasswordException;
import service.UserService;
import service.interfaces.UserServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

public class LoginServlet extends MyHttpServlet {

    public static final long ANONYMOUS = 1;

    private UserServiceInterface userService;

    public LoginServlet() {
        this.userService = new UserService();
    }

    /**
     * present login page
     * @param request
     * @param response
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        initVelocityEngine(request);
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null && !session.getAttribute("userId").equals(Long.valueOf(ANONYMOUS))) {
            response.sendRedirect("/");
        }
        setBasicHtmlResponse(response);
        setReturnHtml("Login");
        outPutHtml(response);
    }

    /**
     * If login successfully, set session
     * @param request
     * @param response
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        initVelocityEngine(request);
        HttpSession session = request.getSession();
        long userId = ANONYMOUS;
        try {
            Map<String, String> parameterMap = getAjaxRequestParameterMap(request.getReader());
            String username = parameterMap.get("username");
            String password = parameterMap.get("password");
            User user = userService.login(username, password);
            userId = user.getId();
            session.setAttribute("userId", userId);
            session.setAttribute("username", username);
            session.setAttribute("lastLoginTime", user.getLastLoginTime());

            JsonWriter jsonWriter = new JsonWriter(response.getWriter());
            jsonWriter.beginObject();
            jsonWriter.name("success").value(true);
            jsonWriter.name("message").value("Login Success");
            jsonWriter.endObject();
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (WrongPasswordException | UserDoesNotExistException e) {
            JsonWriter jsonWriter = new JsonWriter(response.getWriter());
            jsonWriter.beginObject();
            jsonWriter.name("success").value(false);
            jsonWriter.name("message").value(e.getMessage());
            jsonWriter.endObject();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "error happen");
            response.getWriter().println(jsonObject);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


}

package controller.servlets.user;

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
import java.io.IOException;

public class LoginServlet extends MyHttpServlet {

    public static final long ANONYMOUS = 1;

    private UserServiceInterface userService;

    public LoginServlet() {
        this.userService = new UserService();
    }

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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        initVelocityEngine(request);
        HttpSession session = request.getSession();
        long userId = ANONYMOUS;
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userService.login(username, password);
            userId = user.getId();
            session.setAttribute("userId", userId);
            session.setAttribute("username", username);
            session.setAttribute("lastLoginTime", user.getLastLoginTime());
            response.sendRedirect("/");
        } catch (WrongPasswordException | UserDoesNotExistException e) {
            setJsonResponse(response);
            JsonWriter jsonWriter = new JsonWriter(response.getWriter());
            jsonWriter.beginObject();
            jsonWriter.name("success").value(false);
            jsonWriter.name("message").value(e.getMessage());
            jsonWriter.endObject();
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}

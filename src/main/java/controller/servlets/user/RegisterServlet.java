package controller.servlets.user;

import com.google.gson.stream.JsonWriter;
import controller.servlets.MyHttpServlet;
import dao.bean.User;
import exceptions.UserNameHasExistedException;
import service.UserService;
import service.interfaces.UserServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class RegisterServlet extends MyHttpServlet {

    private UserServiceInterface userService;

    public RegisterServlet() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initVelocityEngine(request);
        setBasicHtmlResponse(response);
        setReturnHtml("Register");
        outPutHtml(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // do register
        UserBo user = new UserBo();
        Map<String, String > parameterMap = getAjaxRequestParameterMap(request.getReader());
        user.setUsername(parameterMap.get("username"));
        user.setPassword(parameterMap.get("password"));
        user.setEmail(parameterMap.get("email"));
        JsonWriter jsonWriter = new JsonWriter(response.getWriter());
        try {
            userService.register(user);
            jsonWriter.beginObject().name("success").value(true).name("message").value("Register Success").endObject();
        } catch (UserNameHasExistedException e) {
            jsonWriter.beginObject();
            jsonWriter.name("success").value(false);
            jsonWriter.name("message").value(e.getMessage());
            jsonWriter.endObject();
        }
    }
}

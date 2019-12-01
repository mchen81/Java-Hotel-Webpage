package controller.user;

import dao.bean.User;
import exceptions.UserDoesNotExistException;
import exceptions.WrongPasswordException;
import service.UserService;
import service.interfaces.UserServiceInterface;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    public static final int ANONYMOUS = 1;

    private UserServiceInterface userService;

    public LoginServlet() {
        this.userService = new UserService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        // show login page
        // buffer read html page
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        // get user name and password
        // try { call user service login }
        PrintWriter out = response.getWriter();
        long userId = ANONYMOUS;
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userService.login(username, password);
            userId = user.getId();
            session.setAttribute("userId", userId);
            session.setAttribute("username", username);
            session.setAttribute("lastLoginTime", user.getLastLoginTime());
            // lead to home page with session
        } catch (UserDoesNotExistException e) {
            // TODO return user does not exist by ajax
        } catch (WrongPasswordException e) {
            // TODO return wrong password by ajax
        } catch (Exception e) {
            // TODO return login fail, please input again by ajax
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

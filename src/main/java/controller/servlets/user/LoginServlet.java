package controller.servlets.user;

import controller.servlets.MyHttpServlet;
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

public class LoginServlet extends MyHttpServlet {

    public static final int ANONYMOUS = 1;

    private UserServiceInterface userService;

    public LoginServlet() {
        this.userService = new UserService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        initServlet(request);
        HttpSession session = request.getSession();
        if (session.getAttribute("userID") != null) {
            response.sendRedirect("/index");
        }
        setBasicHtmlResponse(response);
        setReturnHtml("Login");
        outPutHtml(response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        initServlet(request);
        HttpSession session = request.getSession();
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
            response.sendRedirect("/login");
            // lead to home page with session
        } catch (UserDoesNotExistException e) {
            addAttribute("message", "The User name does not exist");
            // TODO return user does not exist by ajax
        } catch (WrongPasswordException e) {
            addAttribute("message", "Password does not match the username");
            // TODO return wrong password by ajax
        } catch (Exception e) {
            addAttribute("message", "Please input again");
            // TODO return login fail, please input again by ajax
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

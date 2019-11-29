package controller.user;

import dao.bean.User;
import exceptions.UserNameHasExistedException;
import service.UserService;
import service.interfaces.UserServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {

    private UserServiceInterface userService;

    public RegisterServlet() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // return register page
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // do register
        User user = new User();
        user.setName(request.getParameter("username"));
        user.setKey(request.getParameter("password"));
        user.setEmailAddress(request.getParameter("email"));
        PrintWriter out = response.getWriter();
        try {
            userService.register(user);
            // TODO return register success and go to home page
        } catch (UserNameHasExistedException e) {
            // TODO return user name has exist by ajax;
        }
    }
}

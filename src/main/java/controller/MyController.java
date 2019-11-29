package controller;

import controller.hotel.HotelSearchingServlet;
import controller.hotel.HotelServlet;
import controller.user.LoginServlet;
import controller.user.RegisterServlet;
import controller.user.UserProfileServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class MyController {

    public static final int PORT = 80;

    public MyController() throws Exception {
        Server server = new Server(PORT);
        ServletHandler handler = new ServletHandler();
        // users
        handler.addServletWithMapping(LoginServlet.class, "/login");
        handler.addServletWithMapping(RegisterServlet.class, "/register");
        handler.addServletWithMapping(UserProfileServlet.class, "/profile");
        // hotel
        handler.addServletWithMapping(HotelServlet.class, "/hotel");
        handler.addServletWithMapping(HotelSearchingServlet.class, "/search");
        // review
        // attractions
        //
        server.setHandler(handler);
        server.start();
        server.join();
    }
}

package controller;

import controller.servlets.hotel.HotelDetailServlet;
import controller.servlets.hotel.HotelSearchingServlet;
import controller.servlets.hotel.HotelServlet;
import controller.servlets.user.LoginServlet;
import controller.servlets.user.LogoutServlet;
import controller.servlets.user.RegisterServlet;
import controller.servlets.user.UserProfileServlet;
import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.nio.file.Paths;

public class MyController {

    public static final int PORT = 8080;

    public MyController() throws Exception {
        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        // Set Context Handler
        String resourceBaseDir = Paths.get("static").toString();
        contextHandler.setResourceBase(resourceBaseDir);
        ServletHolder holder = new ServletHolder("default", DefaultServlet.class);
        holder.setInitParameter("dirAllowed", "false");
        contextHandler.addServlet(holder, "/");
        // set velocity:
        // initialize Velocity
        VelocityEngine velocity = new VelocityEngine();
        velocity.init();
        // set velocity as an attribute of the context so that we can access it
        // from servlets
        contextHandler.setContextPath("/");
        contextHandler.setAttribute("templateEngine", velocity);
        // servlets:
        // users
        contextHandler.addServlet(LoginServlet.class, "/login");
        contextHandler.addServlet(LogoutServlet.class, "/logout");
        contextHandler.addServlet(RegisterServlet.class, "/register");
        contextHandler.addServlet(UserProfileServlet.class, "/profile");
        // hotel
        contextHandler.addServlet(HotelServlet.class, "/hotel");
        contextHandler.addServlet(HotelSearchingServlet.class, "/search");
        contextHandler.addServlet(HotelDetailServlet.class, "/hotelDetail");
        // review
        // attractions
        //
        server.setHandler(contextHandler);
        server.start();
        server.join();
    }
}

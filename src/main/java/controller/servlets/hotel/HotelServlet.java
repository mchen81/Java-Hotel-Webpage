package controller.servlets.hotel;

import controller.servlets.MyHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HotelServlet extends MyHttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initVelocityEngine(request);
        setBasicHtmlResponse(response);
        setReturnHtml("HomePage");
        addAttribute("name", "Jerry");
        outPutHtml(response);
    }
}

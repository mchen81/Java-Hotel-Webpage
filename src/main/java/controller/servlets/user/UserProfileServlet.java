package controller.servlets.user;

import com.google.gson.stream.JsonWriter;
import controller.servlets.MyHttpServlet;
import dao.UserDao;
import dao.bean.Hotel;
import dao.bean.Review;
import dao.bean.User;
import dao.interfaces.UserDaoInterface;
import service.HotelService;
import service.ServicesSingleton;
import service.interfaces.HotelServiceInterface;
import service.interfaces.ReviewServiceInterface;
import service.interfaces.UserServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserProfileServlet extends MyHttpServlet {

    private UserDaoInterface userDaoInterface;

    private UserServiceInterface userService;

    private ReviewServiceInterface reviewService;

    private HotelServiceInterface hotelService = ServicesSingleton.getHotelService();

    public UserProfileServlet() {
        userDaoInterface = new UserDao();
        userService = ServicesSingleton.getUserService();
        reviewService = ServicesSingleton.getReviewService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // return profile page when session > 1
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        initVelocityEngine(request);
        setReturnHtml("UserProfile");
        setBasicHtmlResponse(response);

        if (userId == null || userId <= 1) {
            addAttribute("script", "<script>noUserAlert();</script>>");
            outPutHtml(response);
        } else {
            User user = userDaoInterface.getUserById(userId);
            if (user == null) {
                addAttribute("script", "<script>noUserAlert();</script>>");
                outPutHtml(response);
                return;
            }
            addAttribute("script", "");
            String username = (String) session.getAttribute("username");
            addAttribute("username", username);
            addAttribute("userId", userId);

            // find hotel
            List<String> hotelIds = new ArrayList<>(userService.getSavedHotels(userId.toString()));
            List<Hotel> hotels = new ArrayList<>();
            for(String id : hotelIds){
                hotels.add(hotelService.findHotelById(id));
            }
            addAttribute("hotels", hotels);
            // find reviews
            List<Review> reviews = reviewService.findReviewsByUserId(userId.toString());
            addAttribute("reviews", reviews);

        }
        outPutHtml(response);

    }
}

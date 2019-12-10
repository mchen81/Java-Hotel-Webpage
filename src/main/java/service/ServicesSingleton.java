package service;

/**
 * this Single containing three different services, all services have to be accessed by this class
 */
public class ServicesSingleton {

    private static HotelService hotelService = new HotelService();

    private static ReviewService reviewService = new ReviewService();

    private static UserService userService = new UserService();

    public static HotelService getHotelService() {
        return hotelService;
    }

    public static ReviewService getReviewService() {
        return reviewService;
    }

    public static UserService getUserService() {
        return userService;
    }
}

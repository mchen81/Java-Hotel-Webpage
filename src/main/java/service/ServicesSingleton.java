package service;

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

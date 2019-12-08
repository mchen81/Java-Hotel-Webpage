package exceptions;

public class HotelHasBeenSavedException extends Exception {
    @Override
    public String getMessage() {
        return "You have saved this hotel";
    }
}

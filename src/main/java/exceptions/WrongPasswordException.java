package exceptions;

public class WrongPasswordException extends Exception {
    @Override
    public String getMessage() {
        return "Password does not match the username";
    }
}

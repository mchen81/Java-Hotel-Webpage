package exceptions;

public class DBConnectionFailException extends RuntimeException {
    public DBConnectionFailException() {
        super("Cannot connect to DataBase");
    }

    public DBConnectionFailException(String message) {
        super(message);
    }
}

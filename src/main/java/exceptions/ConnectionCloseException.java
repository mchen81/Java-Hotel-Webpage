package exceptions;

public class ConnectionCloseException extends RuntimeException {
    public ConnectionCloseException() {
        super("Cannot close a connection");
    }
}

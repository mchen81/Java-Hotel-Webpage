package exceptions;

public class QueryException extends RuntimeException {
    public QueryException() {
        super("Something went wrong while doing query");
    }
}

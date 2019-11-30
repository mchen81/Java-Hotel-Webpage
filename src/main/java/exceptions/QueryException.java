package exceptions;

public class QueryException extends RuntimeException {
    public QueryException() {
        super("Something went wrong while doing query");
    }

    public QueryException(int errorCode){
        super("QUERY FAIL: SQL ERROR CODE ->" + errorCode);
    }
}

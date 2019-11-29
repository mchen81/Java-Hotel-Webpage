package exceptions;

public class BlobConvertException extends RuntimeException {
    public BlobConvertException() {
        super("Cannot convert the blob text to String");
    }
}

package pictureboard.api.exception;

public class NotFoundSourceException extends RuntimeException{
    public NotFoundSourceException() {
        super();
    }

    public NotFoundSourceException(String message) {
        super(message);
    }

    public NotFoundSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundSourceException(Throwable cause) {
        super(cause);
    }

    protected NotFoundSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

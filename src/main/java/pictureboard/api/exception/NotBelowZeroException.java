package pictureboard.api.exception;

public class NotBelowZeroException extends RuntimeException{

    public NotBelowZeroException() {
    }

    public NotBelowZeroException(String message) {
        super(message);
    }

    public NotBelowZeroException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotBelowZeroException(Throwable cause) {
        super(cause);
    }

    public NotBelowZeroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

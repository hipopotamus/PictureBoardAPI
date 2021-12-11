package pictureboard.api.exception;

public class SelfRelateException extends RuntimeException{

    public SelfRelateException() {
        super();
    }

    public SelfRelateException(String message) {
        super(message);
    }

    public SelfRelateException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelfRelateException(Throwable cause) {
        super(cause);
    }

    protected SelfRelateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

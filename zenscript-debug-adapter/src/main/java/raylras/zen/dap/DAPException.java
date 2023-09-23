package raylras.zen.dap;

public class DAPException extends RuntimeException {
    public DAPException() {
        super();
    }

    public DAPException(String message) {
        super(message);
    }

    public DAPException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAPException(Throwable cause) {
        super(cause);
    }

    protected DAPException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

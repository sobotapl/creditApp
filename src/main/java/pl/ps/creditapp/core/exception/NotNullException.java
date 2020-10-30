package pl.ps.creditapp.core.exception;


public class NotNullException extends ValidationException {
    public NotNullException(String field) {
        super(String.format("Field %s should be not null",field));
    }
}

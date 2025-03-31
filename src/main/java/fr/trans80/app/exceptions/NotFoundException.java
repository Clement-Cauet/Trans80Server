package fr.trans80.app.exceptions;

public class NotFoundException extends Throwable {
    public NotFoundException(String message, String cause) {
        super(message, new Throwable(cause));
    }
}

package net.darkwire.example.exception;

/**
 * Created by fsiu on 4/19/14.
 */
public class AuthenticationError extends Exception {
    public AuthenticationError(final Throwable throwable) {
        super(throwable);
    }
}

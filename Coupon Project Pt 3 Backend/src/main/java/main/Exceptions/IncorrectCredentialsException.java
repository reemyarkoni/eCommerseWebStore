package main.Exceptions;

public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException() {
        super("Incorrect login credentials");
    }
}

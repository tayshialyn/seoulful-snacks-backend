package com.seoulful.snack.exception;

public class WeakPasswordException extends RuntimeException {

    public WeakPasswordException() {
        super("Password must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters.");
    }
}


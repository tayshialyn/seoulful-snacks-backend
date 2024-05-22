package com.seoulful.snack.exception;

public class PasswordBlankException extends RuntimeException {

    public PasswordBlankException() {
        super("Password cannot be blank.");
    }
}


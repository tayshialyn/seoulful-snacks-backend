package com.seoulful.snack.exception;

public class UnspecifiedException extends RuntimeException {

    public UnspecifiedException() {
        super("An unspecified error occurred. Please try again later.");
    }
}


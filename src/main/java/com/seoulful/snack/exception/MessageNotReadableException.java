package com.seoulful.snack.exception;

public class MessageNotReadableException extends RuntimeException {

    public MessageNotReadableException() {
        super("Unable to read request data.");
    }
}

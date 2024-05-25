package com.feefo.note_web_app_web_service.domain;

public class ConstraintException extends RuntimeException {

    private final String message;

    public ConstraintException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package com.matching.segmentsmatching.rest;

public class JustMessage {

    private String field = "message";
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JustMessage(String message) {
        this.message = message;
    }
    public JustMessage() {}
}

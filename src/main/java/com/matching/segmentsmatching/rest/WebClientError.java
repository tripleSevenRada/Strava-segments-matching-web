package com.matching.segmentsmatching.rest;

public class WebClientError {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public WebClientError(String message) {
        this.message = message;
    }
}

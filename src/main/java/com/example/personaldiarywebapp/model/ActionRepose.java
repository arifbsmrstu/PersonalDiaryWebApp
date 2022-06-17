package com.example.personaldiarywebapp.model;

public class ActionRepose {

    private Boolean result;
    private String message;

    public Boolean getResult() {
        return result;
    }

    public ActionRepose result(Boolean result) {
        this.result = result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ActionRepose message(String message) {
        this.message = message;
        return this;
    }
}

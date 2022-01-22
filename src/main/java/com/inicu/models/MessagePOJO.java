package com.inicu.models;

public class MessagePOJO {

    private String message;
    private String subject;
    private int status_code;

    public MessagePOJO(){
        this.message="";
        this.subject="";
        this.status_code=400;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

package com.inicu.models;

import java.io.Serializable;

public class GeneralResponseObject implements Serializable {
    private boolean status;
    private int statusCode;
    private String message;
    private Object data;
    private PrintoutDropdown printoutDropdown;

    public GeneralResponseObject(){
        this.message="";
        this.data=null;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public PrintoutDropdown getPrintoutDropdown() {
        return printoutDropdown;
    }

    public void setPrintoutDropdown(PrintoutDropdown printoutDropdown) {
        this.printoutDropdown = printoutDropdown;
    }
}

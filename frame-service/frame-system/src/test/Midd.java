package com.snaker.frame.controller.mytest;

public class Midd {
    private String code;
    private String zjID;

    public String getCode() {
        return code;
    }

    public String getZjID() {
        return zjID;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setZjID(String zjID) {
        this.zjID = zjID;
    }

    public Midd(String code,String zjID){
        this.code = code;
        this.zjID = zjID;
    }
}

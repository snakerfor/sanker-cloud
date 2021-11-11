package com.snaker.frame.controller.mytest;

public class Bom {
    private String Id;
    private String parentCode;
    private String childCode;

    public Bom(String Id,String parentCode,String childCode){
        this.Id = Id;
        this.parentCode = parentCode;
        this.childCode = childCode;
    }

    public String getId() {
        return Id;
    }

    public String getParentCode() {
        return parentCode;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

}

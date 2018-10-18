package com.hotix.myhotixguest.models;

public class Info {
    private int type;
    private String text;

    public Info() {
    }

    public Info(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

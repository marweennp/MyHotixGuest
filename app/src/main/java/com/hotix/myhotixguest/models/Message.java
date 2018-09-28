package com.hotix.myhotixguest.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("Details")
    @Expose
    private String details;
    @SerializedName("Tel")
    @Expose
    private String tel;
    @SerializedName("From")
    @Expose
    private String from;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Id")
    @Expose
    private Integer id;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
package com.hotix.myhotixguest.models;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ResaId")
    @Expose
    private Integer resaId;
    @SerializedName("Etat")
    @Expose
    private Integer etat;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Details")
    @Expose
    private ArrayList<CartItem> details = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResaId() {
        return resaId;
    }

    public void setResaId(Integer resaId) {
        this.resaId = resaId;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<CartItem> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<CartItem> details) {
        this.details = details;
    }

}

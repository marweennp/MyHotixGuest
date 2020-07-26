package com.hotix.myhotixguest.models;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("HotelId")
    @Expose
    private Integer hotelId;

    @SerializedName("ResaId")
    @Expose
    private Integer resaId;

    @SerializedName("GroupeId")
    @Expose
    private Integer groupeId;

    @SerializedName("PaxId")
    @Expose
    private Integer paxId;

    @SerializedName("Etat")
    @Expose
    private Integer etat;

    @SerializedName("ProdId")
    @Expose
    private Integer prodId;

    @SerializedName("ProdNum")
    @Expose
    private String prodNum;

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

    public Integer getHotelId() {
        return hotelId;
    }
    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getResaId() {
        return resaId;
    }
    public void setResaId(Integer resaId) {
        this.resaId = resaId;
    }

    public Integer getGroupeId() {
        return groupeId;
    }
    public void setGroupeId(Integer groupeId) {
        this.groupeId = groupeId;
    }

    public Integer getPaxId() {
        return paxId;
    }
    public void setPaxId(Integer paxId) {
        this.paxId = paxId;
    }

    public Integer getEtat() {
        return etat;
    }
    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Integer getProdId() {
        return prodId;
    }
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getProdNum() {
        return prodNum;
    }
    public void setProdNum(String prodNum) {
        this.prodNum = prodNum;
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

package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

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

    @SerializedName("CategorieId")
    @Expose
    private Integer categorieId;

    @SerializedName("WorkStation")
    @Expose
    private String workStation;

    @SerializedName("Details")
    @Expose
    private String details;

    @SerializedName("Tel")
    @Expose
    private String tel;

    @SerializedName("From")
    @Expose
    private String from;

    @SerializedName("Subject")
    @Expose
    private String subject;

    @SerializedName("Origine")
    @Expose
    private String origine;

    @SerializedName("Date")
    @Expose
    private String date;

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

    public Integer getCategorieId() {
        return categorieId;
    }
    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }

    public String getWorkStation() {
        return workStation;
    }
    public void setWorkStation(String workStation) {
        this.workStation = workStation;
    }

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

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOrigine() {
        return origine;
    }
    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ReveilData {

    @SerializedName("hotelID")
    @Expose
    private Integer hotelID;
    @SerializedName("resaID")
    @Expose
    private Integer resaID;
    @SerializedName("resaGroupeId")
    @Expose
    private Integer resaGroupeId;
    @SerializedName("paxID")
    @Expose
    private Integer paxID;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("dateCreation")
    @Expose
    private Date dateCreation;
    @SerializedName("reveilDate")
    @Expose
    private Date reveilDate;
    @SerializedName("reveilHeure")
    @Expose
    private Date reveilHeure;

    public Integer getHotelID() {
        return hotelID;
    }
    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public Integer getResaID() {
        return resaID;
    }
    public void setResaID(Integer resaID) {
        this.resaID = resaID;
    }

    public Integer getResaGroupeId() { return resaGroupeId; }
    public void setResaGroupeId(Integer resaGroupeId) { this.resaGroupeId = resaGroupeId; }

    public Integer getPaxID() { return paxID; }
    public void setPaxID(Integer paxID) { this.paxID = paxID; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Date getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getReveilDatee() {
        return reveilDate;
    }
    public void setReveilDate(Date reveilDate) {
        this.reveilDate = reveilDate;
    }

    public Date getReveilHeure() {
        return reveilHeure;
    }
    public void setReveilHeure(Date reveilHeure) {
        this.reveilHeure = reveilHeure;
    }

}

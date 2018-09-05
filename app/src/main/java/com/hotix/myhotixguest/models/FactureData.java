package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FactureData {

    @SerializedName("DateFacturation")
    @Expose
    private String dateFacturation;
    @SerializedName("Montant")
    @Expose
    private Double montant;
    @SerializedName("Designation")
    @Expose
    private String designation;
    @SerializedName("Arrangement")
    @Expose
    private String arrangement;
    @SerializedName("Comment")
    @Expose
    private String comment;

    public String getDateFacturation() {
        return dateFacturation;
    }

    public void setDateFacturation(String dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getArrangement() {
        return arrangement;
    }

    public void setArrangement(String arrangement) {
        this.arrangement = arrangement;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "FactureData{" +
                "dateFacturation='" + dateFacturation + '\'' +
                ", montant=" + montant +
                ", designation='" + designation + '\'' +
                ", arrangement='" + arrangement + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
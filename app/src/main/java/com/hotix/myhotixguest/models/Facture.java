package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Facture {

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("Annee")
    @Expose
    private Integer annee;

    @SerializedName("DeviseDecimal")
    @Expose
    private Integer deviseDecimal;

    @SerializedName("Devise")
    @Expose
    private String devise;

    @SerializedName("TotalTTC")
    @Expose
    private Double totalTTC;

    @SerializedName("DateFront")
    @Expose
    private String dateFront;

    @SerializedName("LignesFacture")
    @Expose
    private ArrayList<LigneFacture> lignesFacture = null;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getDeviseDecimal() {
        return deviseDecimal;
    }
    public void setDeviseDecimal(Integer deviseDecimal) {
        this.deviseDecimal = deviseDecimal;
    }

    public String getDevise() {
        return devise;
    }
    public void setDevise(String devise) {
        this.devise = devise;
    }

    public Double getTotalTTC() {
        return totalTTC;
    }
    public void setTotalTTC(Double totalTTC) {
        this.totalTTC = totalTTC;
    }

    public String getDateFront() { return dateFront; }
    public void setDateFront(String dateFront) { this.dateFront = dateFront; }

    public ArrayList<LigneFacture> getLignesFacture() {
        return lignesFacture;
    }
    public void setLignesFacture(ArrayList<LigneFacture> lignesFacture) {
        this.lignesFacture = lignesFacture;
    }

}
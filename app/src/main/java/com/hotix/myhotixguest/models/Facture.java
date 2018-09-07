package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facture {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Annee")
    @Expose
    private Integer annee;
    @SerializedName("Devise")
    @Expose
    private String devise;
    @SerializedName("TotalTTC")
    @Expose
    private Double totalTTC;
    @SerializedName("LignesFacture")
    @Expose
    private ArrayList<LignesFacture> lignesFacture = null;

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

    public ArrayList<LignesFacture> getLignesFacture() {
        return lignesFacture;
    }

    public void setLignesFacture(ArrayList<LignesFacture> lignesFacture) {
        this.lignesFacture = lignesFacture;
    }

}
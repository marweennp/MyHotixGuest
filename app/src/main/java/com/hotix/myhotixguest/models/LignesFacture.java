package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LignesFacture {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Annee")
    @Expose
    private Integer annee;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Devise")
    @Expose
    private String devise;
    @SerializedName("ModePaiement")
    @Expose
    private Integer modePaiement;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Montant")
    @Expose
    private Double montant;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public Integer getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(Integer modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

}

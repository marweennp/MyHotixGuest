package com.hotix.myhotixguest.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sejour {

    @SerializedName("ResaId")
    @Expose
    private Integer resaId;
    @SerializedName("FactureId")
    @Expose
    private Integer factureId;
    @SerializedName("FactureAnnee")
    @Expose
    private Integer factureAnnee;
    @SerializedName("NbreA")
    @Expose
    private Integer nbreA;
    @SerializedName("NbreE")
    @Expose
    private Integer nbreE;
    @SerializedName("NbreB")
    @Expose
    private Integer nbreB;
    @SerializedName("Tarif")
    @Expose
    private String tarif;
    @SerializedName("Chambre")
    @Expose
    private String chambre;
    @SerializedName("TypeChambre")
    @Expose
    private String typeChambre;
    @SerializedName("Arrangement")
    @Expose
    private String arrangement;
    @SerializedName("Societe")
    @Expose
    private String societe;
    @SerializedName("DateArrivee")
    @Expose
    private String dateArrivee;
    @SerializedName("DateDepart")
    @Expose
    private String dateDepart;
    @SerializedName("DetailsPax")
    @Expose
    private ArrayList<DetailsPax> detailsPax = null;

    public Integer getResaId() {
        return resaId;
    }

    public void setResaId(Integer resaId) {
        this.resaId = resaId;
    }

    public Integer getFactureId() {
        return factureId;
    }

    public void setFactureId(Integer factureId) {
        this.factureId = factureId;
    }

    public Integer getFactureAnnee() {
        return factureAnnee;
    }

    public void setFactureAnnee(Integer factureAnnee) {
        this.factureAnnee = factureAnnee;
    }

    public Integer getNbreA() {
        return nbreA;
    }

    public void setNbreA(Integer nbreA) {
        this.nbreA = nbreA;
    }

    public Integer getNbreE() {
        return nbreE;
    }

    public void setNbreE(Integer nbreE) {
        this.nbreE = nbreE;
    }

    public Integer getNbreB() {
        return nbreB;
    }

    public void setNbreB(Integer nbreB) {
        this.nbreB = nbreB;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getChambre() {
        return chambre;
    }

    public void setChambre(String chambre) {
        this.chambre = chambre;
    }

    public String getTypeChambre() {
        return typeChambre;
    }

    public void setTypeChambre(String typeChambre) {
        this.typeChambre = typeChambre;
    }

    public String getArrangement() {
        return arrangement;
    }

    public void setArrangement(String arrangement) {
        this.arrangement = arrangement;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public String getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(String dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public ArrayList<DetailsPax> getDetailsPax() {
        return detailsPax;
    }

    public void setDetailsPax(ArrayList<DetailsPax> detailsPax) {
        this.detailsPax = detailsPax;
    }

}

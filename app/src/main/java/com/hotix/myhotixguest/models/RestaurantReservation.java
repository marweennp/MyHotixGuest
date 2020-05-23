package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RestaurantReservation {

    @SerializedName("RestID")
    @Expose
    private Integer restID;
    @SerializedName("EtatResa")
    @Expose
    private Integer etatResa;
    @SerializedName("Reference")
    @Expose
    private Integer reference;
    @SerializedName("NbrPAX")
    @Expose
    private Integer nbrPAX;

    @SerializedName("Nom")
    @Expose
    private String nom;
    @SerializedName("Prenom")
    @Expose
    private String prenom;
    @SerializedName("NumTel")
    @Expose
    private String numTel;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Commentaire")
    @Expose
    private String commentaire;

    @SerializedName("DateResa")
    @Expose
    private Date dateResa;
    @SerializedName("DateArrivee")
    @Expose
    private Date dateArrivee;
    @SerializedName("HeureArrivee")
    @Expose
    private Date heureArrivee;

    public Integer getRestID() {
        return restID;
    }
    public void setRestID(Integer restID) {
        this.restID = restID;
    }

    public Integer getEtatResa() {
        return etatResa;
    }
    public void setEtatResa(Integer etatResa) {
        this.etatResa = etatResa;
    }

    public Integer getReference() { return reference; }
    public void setReference(Integer reference) { this.reference = reference; }

    public Integer getNbrPAX() { return nbrPAX; }
    public void setNbrPAX(Integer nbrPAX) { this.nbrPAX = nbrPAX; }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumTel() {
        return numTel;
    }
    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommentaire() {
        return commentaire;
    }
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateResa() {
        return dateResa;
    }
    public void setDateResa(Date dateResa) {
        this.dateResa = dateResa;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }
    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Date getHeureArrivee() {
        return heureArrivee;
    }
    public void setHeureArrivee(Date heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

}

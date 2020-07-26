package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RestaurantReservation {

    @SerializedName("ResaID")
    @Expose
    private Integer resaID;

    @SerializedName("HotelID")
    @Expose
    private Integer hotelID;

    @SerializedName("RestID")
    @Expose
    private Integer restID;

    @SerializedName("ClientID")
    @Expose
    private Integer clientID;

    @SerializedName("OrigineID")
    @Expose
    private Integer origineID;

    @SerializedName("EtatResa")
    @Expose
    private Integer etatResa;

    @SerializedName("NbrPAX")
    @Expose
    private Integer nbrPAX;

    @SerializedName("Reference")
    @Expose
    private String reference;

    @SerializedName("EtatName")
    @Expose
    private String etatName;

    @SerializedName("RestoName")
    @Expose
    private String restoName;

    @SerializedName("Specialite")
    @Expose
    private String specialite;

    @SerializedName("OrigineCode")
    @Expose
    private String origineCode;

    @SerializedName("OrigineName")
    @Expose
    private String origineName;

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

    public Integer getResaID() {
        return resaID;
    }
    public void setResaID(Integer resaID) {
        this.resaID = resaID;
    }

    public Integer getHotelID() {
        return hotelID;
    }
    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public Integer getRestID() {
        return restID;
    }
    public void setRestID(Integer restID) {
        this.restID = restID;
    }

    public Integer getClientID() {
        return clientID;
    }
    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public Integer getOrigineID() {
        return origineID;
    }
    public void setOrigineID(Integer origineID) {
        this.origineID = origineID;
    }

    public Integer getEtatResa() {
        return etatResa;
    }
    public void setEtatResa(Integer etatResa) {
        this.etatResa = etatResa;
    }

    public Integer getNbrPAX() { return nbrPAX; }
    public void setNbrPAX(Integer nbrPAX) { this.nbrPAX = nbrPAX; }

    public String getEtatName() { return etatName; }
    public void setEtatName(String etatName) { this.etatName = etatName; }

    public String getRestoName() { return restoName; }
    public void setRestoName(String restoName) { this.restoName = restoName; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.restoName = specialite; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getOrigineCode() { return origineCode; }
    public void setOrigineCode(String origineCode) { this.origineCode = origineCode; }

    public String getOrigineName() { return origineName; }
    public void setOrigineName(String origineName) { this.origineName = origineName; }

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

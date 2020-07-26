package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Guest {

    @SerializedName("ClientId")
    @Expose
    private Integer clientId;

    @SerializedName("ResaId")
    @Expose
    private Integer resaId;

    @SerializedName("ResaGroupeId")
    @Expose
    private Integer resaGroupeId;

    @SerializedName("ResaPaxId")
    @Expose
    private Integer resaPaxId;

    @SerializedName("FactureId")
    @Expose
    private Integer factureId;

    @SerializedName("FactureAnnee")
    @Expose
    private Integer factureAnnee;

    @SerializedName("EtatResa")
    @Expose
    private Integer etatResa;

    @SerializedName("NationaliteId")
    @Expose
    private Integer nationaliteId;

    @SerializedName("CiviliteId")
    @Expose
    private Integer civiliteId;

    @SerializedName("Login")
    @Expose
    private String login;

    @SerializedName("Password")
    @Expose
    private String password;

    @SerializedName("Chambre")
    @Expose
    private String chambre;

    @SerializedName("Nom")
    @Expose
    private String nom;

    @SerializedName("Prenom")
    @Expose
    private String prenom;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Phone")
    @Expose
    private String phone;

    @SerializedName("NationaliteName")
    @Expose
    private String nationaliteName;

    @SerializedName("CiviliteName")
    @Expose
    private String civiliteName;

    @SerializedName("Adresse")
    @Expose
    private String adresse;

    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    @SerializedName("iSResident")
    @Expose
    private Boolean iSResident;

    @SerializedName("HasHistory")
    @Expose
    private Boolean hasHistory;

    @SerializedName("DateNaissance")
    @Expose
    private String dateNaissance;

    @SerializedName("DateArrivee")
    @Expose
    private String dateArrivee;

    @SerializedName("DateDepart")
    @Expose
    private String dateDepart;

    public Integer getClientId() {
        return clientId;
    }
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getResaId() {
        return resaId;
    }
    public void setResaId(Integer resaId) {
        this.resaId = resaId;
    }

    public Integer getResaGroupeId() {
        return resaGroupeId;
    }
    public void setResaGroupeId(Integer resaGroupeId) {
        this.resaGroupeId = resaGroupeId;
    }

    public Integer getResaPaxId() {
        return resaPaxId;
    }
    public void setResaPaxId(Integer resaPaxId) {
        this.resaPaxId = resaPaxId;
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

    public Integer getEtatResa() {
        return etatResa;
    }
    public void setEtatResa(Integer etatResa) {
        this.etatResa = etatResa;
    }

    public Integer getNationaliteId() {
        return nationaliteId;
    }
    public void setNationaliteId(Integer nationaliteId) {
        this.nationaliteId = nationaliteId;
    }

    public Integer getCiviliteId() {
        return civiliteId;
    }
    public void setCiviliteId(Integer civiliteId) {
        this.civiliteId = civiliteId;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getChambre() {
        return chambre;
    }
    public void setChambre(String chambre) {
        this.chambre = chambre;
    }

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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationaliteName() {
        return nationaliteName;
    }
    public void setNationaliteName(String nationaliteName) { this.nationaliteName = nationaliteName; }

    public String getCiviliteName() {
        return civiliteName;
    }
    public void setCiviliteName(String civiliteName) {
        this.civiliteName = civiliteName;
    }

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getISResident() {
        return iSResident;
    }
    public void setISResident(Boolean iSResident) {
        this.iSResident = iSResident;
    }

    public Boolean getHasHistory() {
        return hasHistory;
    }
    public void setHasHistory(Boolean hasHistory) {
        this.hasHistory = hasHistory;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
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

}
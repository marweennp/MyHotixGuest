package com.hotix.myhotixguest.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pax {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Nom")
    @Expose
    private String nom;
    @SerializedName("Code")
    @Expose
    private Object code;
    @SerializedName("Prenom")
    @Expose
    private String prenom;
    @SerializedName("DateArrivee")
    @Expose
    private String dateArrivee;
    @SerializedName("DateDepart")
    @Expose
    private String dateDepart;
    @SerializedName("PaysId")
    @Expose
    private Integer paysId;
    @SerializedName("Addresse")
    @Expose
    private String addresse;
    @SerializedName("DateNaissance")
    @Expose
    private String dateNaissance;
    @SerializedName("Lieu")
    @Expose
    private String lieu;
    @SerializedName("Sexe")
    @Expose
    private String sexe;
    @SerializedName("Situation")
    @Expose
    private String situation;
    @SerializedName("Fumeur")
    @Expose
    private Integer fumeur;
    @SerializedName("handicape")
    @Expose
    private Integer handicape;
    @SerializedName("PieceId")
    @Expose
    private Integer pieceId;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("DocNum")
    @Expose
    private String docNum;
    @SerializedName("Gsm")
    @Expose
    private String gsm;
    @SerializedName("Profession")
    @Expose
    private String profession;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("CodePostal")
    @Expose
    private String codePostal;
    @SerializedName("Civilite")
    @Expose
    private String civilite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public Integer getPaysId() {
        return paysId;
    }

    public void setPaysId(Integer paysId) {
        this.paysId = paysId;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Integer getFumeur() {
        return fumeur;
    }

    public void setFumeur(Integer fumeur) {
        this.fumeur = fumeur;
    }

    public Integer getHandicape() {
        return handicape;
    }

    public void setHandicape(Integer handicape) {
        this.handicape = handicape;
    }

    public Integer getPieceId() {
        return pieceId;
    }

    public void setPieceId(Integer pieceId) {
        this.pieceId = pieceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

}
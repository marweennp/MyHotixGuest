package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("Capacite")
    @Expose
    private Integer capacite;

    @SerializedName("Nom")
    @Expose
    private String nom;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("HeureOuvert")
    @Expose
    private String heureOuvert;

    @SerializedName("HeureFermeture")
    @Expose
    private String heureFermeture;

    @SerializedName("NumTel")
    @Expose
    private String numTel;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Specialite")
    @Expose
    private String specialite;



    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCapacite() {
        return capacite;
    }
    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeureOuvert() {
        return heureOuvert;
    }
    public void setHeureOuvert(String heureOuvert) {
        this.heureOuvert = heureOuvert;
    }

    public String getHeureFermeture() {
        return heureFermeture;
    }
    public void setHeureFermeture(String heureFermeture) {
        this.heureFermeture = heureFermeture;
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

    public String getSpecialite() {
        return specialite;
    }
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}

package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("Prix")
    @Expose
    private Double prix;

    @SerializedName("Nom")
    @Expose
    private String nom;

    @SerializedName("Categorie")
    @Expose
    private String categorie;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("Image")
    @Expose
    private String image;

    @SerializedName("Location")
    @Expose
    private String location;

    @SerializedName("Heure")
    @Expose
    private String heure;

    @SerializedName("Etat")
    @Expose
    private String etat;

    @SerializedName("DateDebut")
    @Expose
    private String dateDebut;

    @SerializedName("DateFin")
    @Expose
    private String dateFin;

    public Event() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrix() {
        return prix;
    }
    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getHeure() {
        return heure;
    }
    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getEtat() {
        return etat;
    }
    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

}



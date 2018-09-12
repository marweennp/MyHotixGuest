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
    @SerializedName("DateDebut")
    @Expose
    private String dateDebut;
    @SerializedName("DateFin")
    @Expose
    private String dateFin;
    @SerializedName("Image")
    @Expose
    private Object image;

    public Event(Double prix, String nom, String categorie, String description, String dateDebut, String dateFin) {
        this.prix = prix;
        this.nom = nom;
        this.categorie = categorie;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

}




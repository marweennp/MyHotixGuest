package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItem {

    @SerializedName("Produit")
    @Expose
    private Integer produit;
    @SerializedName("ProduitName")
    @Expose
    private String produitName;
    @SerializedName("Quantite")
    @Expose
    private Integer quantite;
    @SerializedName("PrixUnitaire")
    @Expose
    private Double prixUnitaire;

    public CartItem(Integer produit, String produitName, Integer quantite, Double prixUnitaire) {
        this.produit = produit;
        this.produitName = produitName;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getProduit() {
        return produit;
    }

    public void setProduit(Integer produit) {
        this.produit = produit;
    }

    public String getProduitName() {
        return produitName;
    }

    public void setProduitName(String produitName) {
        this.produitName = produitName;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}

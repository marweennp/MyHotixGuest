package com.hotix.myhotixguest.models;

public class Produit {

    private String name;
    private String price;
    private String category;
    private String family;
    private String subFamily;

    public Produit() {
    }

    public Produit(String name, String price, String category, String family, String subFamily) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.family = family;
        this.subFamily = subFamily;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSubFamily() {
        return subFamily;
    }

    public void setSubFamily(String subFamily) {
        this.subFamily = subFamily;
    }
}

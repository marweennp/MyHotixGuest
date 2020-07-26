package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageCategorie {

    @SerializedName("CategorieID")
    @Expose
    private Integer categorieID;

    @SerializedName("CategorieCode")
    @Expose
    private String categorieCode;

    @SerializedName("CategorieName")
    @Expose
    private String categorieName;

    public Integer getCategorieID() {
        return categorieID;
    }
    public void setICategorieID(Integer categorieID) {
        this.categorieID = categorieID;
    }

    public String getCategorieCode() {
        return categorieCode;
    }
    public void setCategorieCode(String categorieCode) {
        this.categorieCode = categorieCode;
    }

    public String getCategorieName() {
        return categorieName;
    }
    public void setCategorieName(String categorieName) {
        this.categorieName = categorieName;
    }

}
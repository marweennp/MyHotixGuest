package com.hotix.myhotixguest.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Famille {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Logo")
    @Expose
    private String logo;
    @SerializedName("sFamilles")
    @Expose
    private List<SFamille> sFamilles = null;

    public Famille(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<SFamille> getSFamilles() {
        return sFamilles;
    }

    public void setSFamilles(List<SFamille> sFamilles) {
        this.sFamilles = sFamilles;
    }

}
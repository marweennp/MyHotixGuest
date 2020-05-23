package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviseChange {

    @SerializedName("DeviseCodeISO")
    @Expose
    private String deviseCodeISO;

    @SerializedName("DeviseName")
    @Expose
    private String deviseName;

    @SerializedName("DeviseUnite")
    @Expose
    private Integer deviseUnite;

    @SerializedName("DevisTauxVente")
    @Expose
    private Double devisTauxVente;

    @SerializedName("DevisTauxAchat")
    @Expose
    private Double devisTauxAchat;

    public String getDeviseCodeISO() {
        return deviseCodeISO;
    }
    public void setDeviseCodeISO(String deviseCodeISO) {
        this.deviseCodeISO = deviseCodeISO;
    }

    public String getDeviseName() {
        return deviseName;
    }
    public void setDeviseName(String deviseName) {
        this.deviseName = deviseName;
    }

    public Integer getDeviseUnite() {
        return deviseUnite;
    }
    public void setDeviseUnite(Integer deviseUnite) {
        this.deviseUnite = deviseUnite;
    }

    public Double getDevisTauxVente() {
        return devisTauxVente;
    }
    public void setDevisTauxVente(Double devisTauxVente) {
        this.devisTauxVente = devisTauxVente;
    }

    public Double getDevisTauxAchat() {
        return devisTauxAchat;
    }
    public void setDevisTauxAchat(Double devisTauxAchat) {
        this.devisTauxAchat = devisTauxAchat;
    }
}

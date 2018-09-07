package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsPax {

    @SerializedName("ClientId")
    @Expose
    private Integer clientId;
    @SerializedName("ClientNom")
    @Expose
    private String clientNom;
    @SerializedName("ClientPrenom")
    @Expose
    private String clientPrenom;
    @SerializedName("ClientDateNaissance")
    @Expose
    private String clientDateNaissance;
    @SerializedName("ClientAdresse")
    @Expose
    private String clientAdresse;
    @SerializedName("ClientEmail")
    @Expose
    private String clientEmail;
    @SerializedName("ClientPhone")
    @Expose
    private String clientPhone;
    @SerializedName("ClientNationalite")
    @Expose
    private String clientNationalite;
    @SerializedName("isMatser")
    @Expose
    private Boolean isMatser;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientNom() {
        return clientNom;
    }

    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    public String getClientPrenom() {
        return clientPrenom;
    }

    public void setClientPrenom(String clientPrenom) {
        this.clientPrenom = clientPrenom;
    }

    public String getClientDateNaissance() {
        return clientDateNaissance;
    }

    public void setClientDateNaissance(String clientDateNaissance) {
        this.clientDateNaissance = clientDateNaissance;
    }

    public String getClientAdresse() {
        return clientAdresse;
    }

    public void setClientAdresse(String clientAdresse) {
        this.clientAdresse = clientAdresse;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientNationalite() {
        return clientNationalite;
    }

    public void setClientNationalite(String clientNationalite) {
        this.clientNationalite = clientNationalite;
    }

    public Boolean getIsMatser() {
        return isMatser;
    }

    public void setIsMatser(Boolean isMatser) {
        this.isMatser = isMatser;
    }

}
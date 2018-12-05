package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contacte {

    @SerializedName("IdContact")
    @Expose
    private Integer idContact;
    @SerializedName("NomContact")
    @Expose
    private String nomContact;
    @SerializedName("TypeContact")
    @Expose
    private Integer typeContact;

    public Integer getIdContact() {
        return idContact;
    }

    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public Integer getTypeContact() {
        return typeContact;
    }

    public void setTypeContact(Integer typeContact) {
        this.typeContact = typeContact;
    }

}
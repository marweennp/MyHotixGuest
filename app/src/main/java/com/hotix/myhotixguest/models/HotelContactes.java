package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HotelContactes {

    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Contactes")
    @Expose
    private ArrayList<Contacte> contactes = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Contacte> getContactes() {
        return contactes;
    }

    public void setContactes(ArrayList<Contacte> contactes) {
        this.contactes = contactes;
    }

}
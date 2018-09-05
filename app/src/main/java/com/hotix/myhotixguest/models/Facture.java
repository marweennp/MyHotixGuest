package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Facture {

    @SerializedName("data")
    @Expose
    private ArrayList<FactureData> data = null;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public ArrayList<FactureData> getData() {
        return data;
    }

    public void setData(ArrayList<FactureData> data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}

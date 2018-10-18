package com.hotix.myhotixguest.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearbyPlaces {

    @SerializedName("results")
    @Expose
    private ArrayList<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

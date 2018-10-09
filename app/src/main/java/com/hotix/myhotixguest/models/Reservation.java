package com.hotix.myhotixguest.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reservation {

    @SerializedName("data")
    @Expose
    private List<Pax> data = null;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public List<Pax> getData() {
        return data;
    }

    public void setData(List<Pax> data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}

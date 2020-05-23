package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Arrangement {

    @SerializedName("ArrangID")
    @Expose
    private Integer arrangID;

    @SerializedName("HotelID")
    @Expose
    private Integer hotelID;

    @SerializedName("ArrangCode")
    @Expose
    private String arrangCode;

    @SerializedName("ArrangName")
    @Expose
    private String arrangName;

    public Integer getArrangID() {
        return arrangID;
    }
    public void setArrangID(Integer arrangID) {
        this.arrangID = arrangID;
    }

    public Integer getHotelID() {
        return hotelID;
    }
    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public String getArrangCode() {
        return arrangCode;
    }
    public void setArrangCode(String arrangCode) {
        this.arrangCode = arrangCode;
    }

    public String getArrangName() {
        return arrangName;
    }
    public void setArrangName(String arrangName) {
        this.arrangName = arrangName;
    }
}

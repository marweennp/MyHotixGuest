package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MobileResa {

    @SerializedName("ArrangeID")
    @Expose
    private Integer arrangeID;
    @SerializedName("RoomType")
    @Expose
    private Integer roomType;
    @SerializedName("NbA")
    @Expose
    private Integer nbA;
    @SerializedName("NbE")
    @Expose
    private Integer nbE;
    @SerializedName("NbB")
    @Expose
    private Integer nbB;
    @SerializedName("DateStart")
    @Expose
    private String dateStart;
    @SerializedName("DateEnd")
    @Expose
    private String dateEnd;

    public Integer getArrangeID() {
        return arrangeID;
    }

    public void setArrangeID(Integer arrangeID) {
        this.arrangeID = arrangeID;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Integer getNbA() { return nbA; }

    public void setNbA(Integer nbA) { this.nbA = nbA; }

    public Integer getNbE() { return nbE; }

    public void setNbE(Integer nbE) { this.nbE = nbE; }

    public Integer getNbB() { return nbB; }

    public void setNbB(Integer nbB) { this.nbB = nbB; }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

}

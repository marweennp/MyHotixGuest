package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomType {

    @SerializedName("TypeHebergID")
    @Expose
    private Integer typeHebergID;

    @SerializedName("RoomTypeID")
    @Expose
    private Integer roomTypeID;

    @SerializedName("HotelID")
    @Expose
    private Integer hotelID;

    @SerializedName("RoomTypeNbrOcc")
    @Expose
    private Integer roomTypeNbrOcc;

    @SerializedName("RoomTypeBeds")
    @Expose
    private Integer roomTypeBeds;

    @SerializedName("RoomTypeMaxOcc")
    @Expose
    private Integer roomTypeMaxOcc;

    @SerializedName("RoomTypeMinOcc")
    @Expose
    private Integer roomTypeMinOcc;

    @SerializedName("RoomTypeCode")
    @Expose
    private String roomTypeCode;

    @SerializedName("RoomTypeName")
    @Expose
    private String roomTypeName;

    public Integer getTypeHebergID() {
        return typeHebergID;
    }
    public void setTypeHebergID(Integer typeHebergID) {
        this.typeHebergID = typeHebergID;
    }

    public Integer getRoomTypeID() {
        return roomTypeID;
    }
    public void setRoomTypeID(Integer roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public Integer getHotelID() {
        return hotelID;
    }
    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public Integer getRoomTypeNbrOcc() {
        return roomTypeNbrOcc;
    }
    public void setRoomTypeNbrOcc(Integer roomTypeNbrOcc) {
        this.roomTypeNbrOcc = roomTypeNbrOcc;
    }

    public Integer getRoomTypeBeds() {
        return roomTypeBeds;
    }
    public void setRoomTypeBeds(Integer roomTypeBeds) {
        this.roomTypeBeds = roomTypeBeds;
    }

    public Integer getRoomTypeMaxOcc() {
        return roomTypeMaxOcc;
    }
    public void setRoomTypeMaxOcc(Integer roomTypeMaxOcc) {
        this.roomTypeMaxOcc = roomTypeMaxOcc;
    }

    public Integer getRoomTypeMinOcc() {
        return roomTypeMinOcc;
    }
    public void setRoomTypeMinOcc(Integer roomTypeMinOcc) {
        this.roomTypeMinOcc = roomTypeMinOcc;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }
    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }
}

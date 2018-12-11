package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HotelInfos {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("HotelName")
    @Expose
    private String hotelName;
    @SerializedName("HotelAdresse")
    @Expose
    private String hotelAdresse;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("EventMail")
    @Expose
    private String eventMail;
    @SerializedName("HotelContactes")
    @Expose
    private ArrayList<HotelContactes> hotelContactes = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAdresse() {
        return hotelAdresse;
    }

    public void setHotelAdresse(String hotelAdresse) {
        this.hotelAdresse = hotelAdresse;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEventMail() { return eventMail; }

    public void setEventMail(String eventMail) { this.eventMail = eventMail; }

    public ArrayList<HotelContactes> getHotelContactes() {
        return hotelContactes;
    }

    public void setHotelContactes(ArrayList<HotelContactes> hotelContactes) {
        this.hotelContactes = hotelContactes;
    }

}

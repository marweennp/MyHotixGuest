package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingAvailability {

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Error")
    @Expose
    private Integer error;

    @SerializedName("Success")
    @Expose
    private Boolean success;

    @SerializedName("Available")
    @Expose
    private Boolean available;

    @SerializedName("Price")
    @Expose
    private Double price;


    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getError() {
        return error;
    }
    public void setError(Integer error) {
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean prizeOrder) {
        this.success = success;
    }

    public Boolean getAvailable() {
        return available;
    }
    public void setAvailable(Boolean available) { this.available = available; }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) { this.price = price; }
}

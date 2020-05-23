package com.hotix.myhotixguest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingConfirmation {

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Error")
    @Expose
    private Integer error;

    @SerializedName("Success")
    @Expose
    private Boolean success;

    @SerializedName("ResaId")
    @Expose
    private Integer resaId;


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

    public Integer getResaId() {
        return resaId;
    }
    public void setResaId(Integer resaId) { this.resaId = resaId; }
}

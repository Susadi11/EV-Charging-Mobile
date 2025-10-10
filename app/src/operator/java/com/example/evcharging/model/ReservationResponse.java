package com.example.evcharging.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservationResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Reservation> data;

    @SerializedName("message")
    private String message;

    @SerializedName("errors")
    private Object errors;

    public boolean isSuccess() {
        return success;
    }

    public List<Reservation> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Object getErrors() {
        return errors;
    }
}

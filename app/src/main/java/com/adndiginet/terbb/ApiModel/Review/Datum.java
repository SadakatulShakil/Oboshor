
package com.adndiginet.terbb.ApiModel.Review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("message")
    @Expose
    private String message;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Datum{" +
                "date='" + date + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

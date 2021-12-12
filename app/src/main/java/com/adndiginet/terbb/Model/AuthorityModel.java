package com.adndiginet.terbb.Model;

import java.io.Serializable;

public class AuthorityModel implements Serializable {

    private String name;
    private int images;
    private String designation;
    private String quotes;

    public AuthorityModel() {
    }

    public AuthorityModel(String name, int images, String designation, String quotes) {
        this.name = name;
        this.images = images;
        this.designation = designation;
        this.quotes = quotes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return "AuthorityModel{" +
                "name='" + name + '\'' +
                ", images=" + images +
                ", designation='" + designation + '\'' +
                ", quotes='" + quotes + '\'' +
                '}';
    }
}

package edu.uoc.library.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sgar810 on 27/09/2016.
 */
public class MonumentList {

    @SerializedName("monuments")
    private List<Monument> monuments;

    public List<Monument> getMonuments() {
        return monuments;
    }

    public void setMonuments(List<Monument> monuments) {
        this.monuments = monuments;
    }
}

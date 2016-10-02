package edu.uoc.library.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SGAR810 on 23/09/2016.
 */
public class Monument {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    /**
     * Method to get the id of the monument
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Method to set the id of the monument
     * @return id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method to get the name of the monument
     * @return item name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the name of the monument
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to get the description of the monument
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set the description of the monument
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method to get the Image of the monument
     * @return
     */
    public String getImagePath() {
        return image;
    }

    /**
     * Method to set the Image of the monument
     * @param image
     */
    public void setImagePath(String image) {
        this.image = image;
    }

    /**
     * Method to get the country of the monument
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method to set the city of the monument
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}

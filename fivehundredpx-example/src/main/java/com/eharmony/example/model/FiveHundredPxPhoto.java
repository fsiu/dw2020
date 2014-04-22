package com.eharmony.example.model;

import com.google.gson.annotations.SerializedName;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by fsiu on 3/21/14.
 */
public class FiveHundredPxPhoto {

    private long id;

    @JsonProperty("user_id")
    @SerializedName("user_id")
    private long userId;

    private String name;
    private String description;

    @JsonProperty("image_url")
    @SerializedName("image_url")
    private String imageUrl;

    @JsonProperty("shutter_speed")
    @SerializedName("shutter_speed")
    private String shutterSpeed;

    private ArrayList<FiveHundredPxImageMetadata> images;

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public ArrayList<FiveHundredPxImageMetadata> getImages() {
        return images;
    }
}

package net.darkwire.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fsiu on 3/21/14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiveHundredPxPhoto {

    private String name;

    @JsonProperty("image_url")
    @SerializedName("image_url")
    private String imageUrl;

    @JsonProperty("shutter_speed")
    @SerializedName("shutter_speed")
    private String shutterSpeed;

    private ArrayList<FiveHundredPxImageMetadata> images;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public void setImages(ArrayList<FiveHundredPxImageMetadata> images) {
        this.images = images;
    }

    public ArrayList<FiveHundredPxImageMetadata> getImages() {
        return images;
    }
}

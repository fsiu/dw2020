package com.eharmony.example.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by fsiu on 3/21/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FiveHundredPxPhotoContainer {

    @JsonProperty("current_page")
    @SerializedName("current_page")
    private int currentPage;

    @JsonProperty("total_pages")
    @SerializedName("total_pages")
    private int totalPages;

    private ArrayList<FiveHundredPxPhoto> photos;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public ArrayList<FiveHundredPxPhoto> getPhotos() {
        return photos;
    }
}

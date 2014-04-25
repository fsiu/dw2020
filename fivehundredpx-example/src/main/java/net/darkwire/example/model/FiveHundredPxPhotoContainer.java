package net.darkwire.example.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;


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

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<FiveHundredPxPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<FiveHundredPxPhoto> photos) {
        this.photos = photos;
    }
}

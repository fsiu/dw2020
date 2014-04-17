package com.eharmony.example.model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fsiu on 3/21/14.
 */
public class FiveHundredPxPhotoContainer {

    @SerializedName("current_page")
    private int currentPage;

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

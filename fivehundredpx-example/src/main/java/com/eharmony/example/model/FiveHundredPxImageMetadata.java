package com.eharmony.example.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fsiu on 3/21/14.
 */
public class FiveHundredPxImageMetadata {
    private int size;
    private String url;

    @SerializedName("https_url")
    private String httpsUrl;

    public int getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpsUrl() {
        return httpsUrl;
    }
}

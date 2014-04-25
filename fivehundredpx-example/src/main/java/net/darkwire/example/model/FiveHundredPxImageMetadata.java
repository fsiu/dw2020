package net.darkwire.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsiu on 3/21/14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiveHundredPxImageMetadata {
    private int size;
    private String url;

    @JsonProperty("https_url")
    @SerializedName("https_url")
    private String httpsUrl;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpsUrl() {
        return httpsUrl;
    }

    public void setHttpsUrl(String httpsUrl) {
        this.httpsUrl = httpsUrl;
    }
}

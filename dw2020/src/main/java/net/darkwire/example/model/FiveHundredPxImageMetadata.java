package net.darkwire.example.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fsiu on 3/21/14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiveHundredPxImageMetadata implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.size);
        dest.writeString(this.url);
        dest.writeString(this.httpsUrl);
    }

    public FiveHundredPxImageMetadata() {
    }

    private FiveHundredPxImageMetadata(Parcel in) {
        this.size = in.readInt();
        this.url = in.readString();
        this.httpsUrl = in.readString();
    }

    public static Parcelable.Creator<FiveHundredPxImageMetadata> CREATOR = new Parcelable.Creator<FiveHundredPxImageMetadata>() {
        public FiveHundredPxImageMetadata createFromParcel(Parcel source) {
            return new FiveHundredPxImageMetadata(source);
        }

        public FiveHundredPxImageMetadata[] newArray(int size) {
            return new FiveHundredPxImageMetadata[size];
        }
    };
}

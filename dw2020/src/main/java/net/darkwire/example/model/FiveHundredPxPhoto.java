package net.darkwire.example.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fsiu on 3/21/14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiveHundredPxPhoto implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeString(this.shutterSpeed);
        dest.writeList(images);
    }

    public FiveHundredPxPhoto() {
    }

    private FiveHundredPxPhoto(Parcel in) {
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.shutterSpeed = in.readString();
        this.images = in.readArrayList(FiveHundredPxImageMetadata.class.getClassLoader());
    }

    public static Parcelable.Creator<FiveHundredPxPhoto> CREATOR = new Parcelable.Creator<FiveHundredPxPhoto>() {
        public FiveHundredPxPhoto createFromParcel(Parcel source) {
            return new FiveHundredPxPhoto(source);
        }

        public FiveHundredPxPhoto[] newArray(int size) {
            return new FiveHundredPxPhoto[size];
        }
    };
}

package com.fivehundredpx.api.auth;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import android.os.Parcel;
import android.os.Parcelable;

import com.fivehundredpx.api.FiveHundredException;

public class AccessToken implements Parcelable {

	private String token;
	private String tokenSecret;

	public AccessToken(String token, String tokenSecret) {
		this.token = token;
		this.tokenSecret = tokenSecret;
	}
	
	public AccessToken(Parcel in) {
		readFromParcel(in);
	}

	AccessToken(HttpResponse response) throws FiveHundredException {
		try {
			final String responseString = EntityUtils.toString(response
					.getEntity());

			this.tokenSecret = HttpParameterUtil.getUrlParamValue(
					responseString, "oauth_token_secret");
			this.token = HttpParameterUtil.getUrlParamValue(responseString,
					"oauth_token");

		} catch (ParseException e) {
			throw new FiveHundredException(e);
		} catch (IOException e) {
			throw new FiveHundredException(e);
		}

	}

	public String getToken() {
		return token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public static final Creator<AccessToken> CREATOR = new Creator<AccessToken>() {
		public AccessToken createFromParcel(Parcel in) {
			return new AccessToken(in);
		}

		public AccessToken[] newArray(int size) {
			return new AccessToken[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(token);
		dest.writeString(tokenSecret);
	}
 
	private void readFromParcel(Parcel in) {
		token = in.readString();
		tokenSecret = in.readString();
	}
}

package com.fivehundredpx.api.auth;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class FacebookAuthProvider extends XAuthProvider {

	private String facebookToken;

	public FacebookAuthProvider(String facebookToken) {
		super();
		this.facebookToken = facebookToken;
	}

	protected ArrayList<NameValuePair> buildParameters(OAuthParameters params) {
		ArrayList<NameValuePair> tuples = new ArrayList<NameValuePair>();
		tuples.add(new BasicNameValuePair(OAuthConstants.MODE, "facebook_auth"));
		params.put(OAuthConstants.MODE, "facebook_auth");
		tuples.add(new BasicNameValuePair(OAuthConstants.X_TOKEN, facebookToken));
		params.put(OAuthConstants.X_TOKEN, facebookToken);

		return tuples;
	}
	
}

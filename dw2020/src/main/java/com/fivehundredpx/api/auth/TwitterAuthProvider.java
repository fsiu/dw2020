package com.fivehundredpx.api.auth;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class TwitterAuthProvider extends XAuthProvider {
	
	private String twitterToken;
	private String twitterSecret;

	public TwitterAuthProvider(String twitterToken, String twitterSecret) {
		super();
		this.twitterToken = twitterToken;
		this.twitterSecret = twitterSecret;
	}

	protected ArrayList<NameValuePair> buildParameters(OAuthParameters params) {
		ArrayList<NameValuePair> tuples = new ArrayList<NameValuePair>();
		tuples.add(new BasicNameValuePair(OAuthConstants.MODE, "twitter_auth"));
		params.put(OAuthConstants.MODE, "twitter_auth");
		tuples.add(new BasicNameValuePair(OAuthConstants.X_TOKEN, twitterToken));
		params.put(OAuthConstants.X_TOKEN, twitterToken);
		tuples.add(new BasicNameValuePair(OAuthConstants.X_SECRET, twitterSecret));
		params.put(OAuthConstants.X_SECRET, twitterSecret);

		return tuples;
	}
}

package com.fivehundredpx.api.auth;

import org.apache.http.client.methods.HttpPost;

import com.fivehundredpx.api.FiveHundredException;

public interface OAuthProvider {
	void signForAccessToken(HttpPost req) throws FiveHundredException;
	
	void setOAuthConsumer(String consumerKey, String consumerSecret);
	void setOAuthRequestToken(String requestTokenKey, String requestTokenSecret);
}
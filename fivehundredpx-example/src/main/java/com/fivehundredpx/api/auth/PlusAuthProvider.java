package com.fivehundredpx.api.auth;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class PlusAuthProvider extends XAuthProvider {

    private String plusToken;

    public PlusAuthProvider(String plusToken) {
        super();
        this.plusToken = plusToken;
    }

    protected ArrayList<NameValuePair> buildParameters(OAuthParameters params) {
        ArrayList<NameValuePair> tuples = new ArrayList<NameValuePair>();
        tuples.add(new BasicNameValuePair(OAuthConstants.MODE, "google_oauth2_auth"));
        params.put(OAuthConstants.MODE, "google_oauth2_auth");
        tuples.add(new BasicNameValuePair(OAuthConstants.X_TOKEN, plusToken));
        params.put(OAuthConstants.X_TOKEN, plusToken);

        return tuples;
    }

}

package com.eharmony.example.service.client;

import com.eharmony.example.model.FiveHundredPxConfiguration;
import com.fivehundredpx.api.auth.AccessToken;

import se.akerfeldt.signpost.retrofit.RetrofitHttpOAuthConsumer;
import se.akerfeldt.signpost.retrofit.SigningOkClient;

/**
 * Created by fsiu on 3/21/14.
 */
public enum FiveHundredPxClient {
    INSTANCE;

    private RetrofitHttpOAuthConsumer oAuthConsumer;

    public void setConsumer(final RetrofitHttpOAuthConsumer oAuthConsumer) {
        this.oAuthConsumer = oAuthConsumer;
    }

    public void setConsumer(final AccessToken accessToken) {
        final FiveHundredPxConfiguration fiveHundredPxConfiguration = FiveHundredPxConfiguration.INSTANCE;
        final RetrofitHttpOAuthConsumer oAuthConsumer = new RetrofitHttpOAuthConsumer(
                fiveHundredPxConfiguration.getConsumerKey(),
                fiveHundredPxConfiguration.getConsumerSecret());
        oAuthConsumer.setTokenWithSecret(accessToken.getToken(), accessToken.getTokenSecret());
        this.oAuthConsumer = oAuthConsumer;
    }

    public SigningOkClient getClient() {
        return new SigningOkClient(this.oAuthConsumer);
    }
}

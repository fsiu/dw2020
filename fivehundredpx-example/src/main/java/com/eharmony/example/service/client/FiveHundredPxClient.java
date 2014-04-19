package com.eharmony.example.service.client;

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

    public SigningOkClient getClient() {
        return new SigningOkClient(this.oAuthConsumer);
    }
}

package com.eharmony.example.service;

import retrofit.RestAdapter;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import com.eharmony.example.service.catalog.UrlCatalog;
import com.eharmony.example.service.client.FiveHundredPxClient;

/**
 * Created by fsiu on 3/21/14.
 */
public class FiveHundredPxSpiceService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(FiveHundredPx.class);
    }

    @Override
    protected String getServerUrl() {
        return UrlCatalog.FIVE_HUNDRED_PX_URL;
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        return super.createRestAdapterBuilder().setClient(FiveHundredPxClient.INSTANCE.getClient());
    }

}

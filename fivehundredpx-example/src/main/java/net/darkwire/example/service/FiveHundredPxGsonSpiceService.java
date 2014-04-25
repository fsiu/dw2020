package net.darkwire.example.service;

import retrofit.RestAdapter;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import net.darkwire.example.service.catalog.UrlCatalog;
import net.darkwire.example.service.client.FiveHundredPxClient;

/**
 * Created by fsiu on 3/21/14.
 */
public class FiveHundredPxGsonSpiceService extends RetrofitGsonSpiceService {

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

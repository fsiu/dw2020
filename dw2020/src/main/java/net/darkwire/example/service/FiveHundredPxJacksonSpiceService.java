package net.darkwire.example.service;

import net.darkwire.example.service.catalog.UrlCatalog;
import net.darkwire.example.service.client.FiveHundredPxClient;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.octo.android.robospice.retrofit.RetrofitJackson2SpiceService;

import retrofit.RestAdapter;

/**
 * Created by fsiu on 3/21/14.
 */
public class FiveHundredPxJacksonSpiceService extends RetrofitJackson2SpiceService {

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

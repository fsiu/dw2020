package net.darkwire.example.service;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by fsiu on 4/24/14.
 */
public abstract class FiveHundredPxSpiceRequest<T,V> extends RetrofitSpiceRequest<T,V> implements CacheableSpiceRequest{

    public FiveHundredPxSpiceRequest(Class<T> originalClazz, Class<V> retrofittedClazz) {
        super(originalClazz, retrofittedClazz);
    }

}

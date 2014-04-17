package com.eharmony.example.service;

import com.eharmony.example.service.catalog.UrlCatalog;
import retrofit.http.GET;

import com.eharmony.example.model.FiveHundredPxPhotoContainer;
import retrofit.http.Query;

/**
 * Created by fsiu on 3/21/14.
 */
public interface FiveHundredPx {

    @GET(UrlCatalog.FIVE_HUNDRED_PX_PHOTOS_URI)
    FiveHundredPxPhotoContainer getPhotos(@Query("page") final int page, @Query("rpp")final int resultsPerPage);

}

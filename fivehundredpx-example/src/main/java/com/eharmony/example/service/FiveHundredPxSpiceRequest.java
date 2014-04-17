package com.eharmony.example.service;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import com.eharmony.example.model.FiveHundredPxPhotoContainer;

/**
 * Created by fsiu on 3/21/14.
 */
public class FiveHundredPxSpiceRequest extends RetrofitSpiceRequest<FiveHundredPxPhotoContainer, FiveHundredPx>{

    private int page;
    private int resultPerPage;

    public FiveHundredPxSpiceRequest(final int page, final int resultPerPage) {
        super(FiveHundredPxPhotoContainer.class, FiveHundredPx.class);
        this.page = page;
        this.resultPerPage = resultPerPage;
    }

    @Override
    public FiveHundredPxPhotoContainer loadDataFromNetwork() {
        return getService().getPhotos(this.page, this.resultPerPage);
    }

}

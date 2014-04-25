package net.darkwire.example.service;

import net.darkwire.example.model.FiveHundredPxPhotoContainer;

/**
 * Created by fsiu on 3/21/14.
 */
public class FiveHundredPxRecentPhotosSpiceRequest extends FiveHundredPxSpiceRequest<FiveHundredPxPhotoContainer, FiveHundredPx> {

    private long page;
    private int resultPerPage;

    public FiveHundredPxRecentPhotosSpiceRequest(final long page, final int resultPerPage) {
        super(FiveHundredPxPhotoContainer.class, FiveHundredPx.class);
        this.page = page;
        this.resultPerPage = resultPerPage;
    }

    @Override
    public FiveHundredPxPhotoContainer loadDataFromNetwork() {
        return getService().getPhotos(this.page, this.resultPerPage);
    }

    @Override
    public Object getCacheKey() {
        return this.hashCode()+page+""+resultPerPage;
    }

}

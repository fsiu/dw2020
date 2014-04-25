package net.darkwire.example.service;

import net.darkwire.example.model.FiveHundredPxPhotoContainer;

/**
 * Created by fsiu on 3/21/14.
 */
public class FiveHundredPxSearchSpiceRequest extends FiveHundredPxSpiceRequest<FiveHundredPxPhotoContainer, FiveHundredPx> {

    private final String term;
    private final String tag;
    private final long page;
    private final int resultPerPage;

    public FiveHundredPxSearchSpiceRequest(final String term, final String tag, final long page, final int resultPerPage) {
        super(FiveHundredPxPhotoContainer.class, FiveHundredPx.class);
        this.term = term;
        this.tag = tag;
        this.page = page;
        this.resultPerPage = resultPerPage;
    }

    @Override
    public FiveHundredPxPhotoContainer loadDataFromNetwork() {
        return getService().getPhotos(this.term, this.tag, this.page, this.resultPerPage);
    }

    @Override
    public Object getCacheKey() {
        return this.hashCode()+term+tag+page+""+resultPerPage;
    }

}

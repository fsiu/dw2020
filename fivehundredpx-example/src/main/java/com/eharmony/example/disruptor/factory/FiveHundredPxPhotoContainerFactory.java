package com.eharmony.example.disruptor.factory;

import com.eharmony.example.model.FiveHundredPxPhotoContainer;
import com.lmax.disruptor.EventFactory;

/**
 * Created by fsiu on 4/22/14.
 */
public class FiveHundredPxPhotoContainerFactory implements EventFactory<FiveHundredPxPhotoContainer> {
    public FiveHundredPxPhotoContainer newInstance() {
        return new FiveHundredPxPhotoContainer();
    }
}

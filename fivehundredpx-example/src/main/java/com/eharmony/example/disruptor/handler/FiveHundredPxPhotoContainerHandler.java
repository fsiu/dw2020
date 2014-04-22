package com.eharmony.example.disruptor.handler;

import com.eharmony.example.model.FiveHundredPxPhotoContainer;
import com.lmax.disruptor.EventHandler;

/**
 * Created by fsiu on 4/22/14.
 */
public class FiveHundredPxPhotoContainerHandler implements EventHandler<FiveHundredPxPhotoContainer> {

    public FiveHundredPxPhotoContainerHandler() {

    }

    public void onEvent(FiveHundredPxPhotoContainer event, long sequence, boolean endOfBatch)
    {
        System.out.println("Event: " + event);
    }
}

package com.eharmony.example.disruptor.handler;

import android.os.Handler;

import com.eharmony.example.model.FiveHundredPxPhotoContainer;
import com.eharmony.example.widgets.adapter.PhotoAdapter;
import com.lmax.disruptor.EventHandler;

/**
 * Created by fsiu on 4/22/14.
 */
public class FiveHundredPxPhotoContainerHandler implements EventHandler<FiveHundredPxPhotoContainer> {

    private final PhotoAdapter photoAdapter;
    private final Handler handler;

    public FiveHundredPxPhotoContainerHandler(final PhotoAdapter photoAdapter, final Handler handler) {
        this.photoAdapter = photoAdapter;
        this.handler = handler;
    }

    public void onEvent(final FiveHundredPxPhotoContainer event, long sequence, boolean endOfBatch)
    {
        this.photoAdapter.incrementPage();
        this.photoAdapter.setMaxPages(event.getTotalPages());

        handler.post(new Runnable() {
            @Override
            public void run() {
                FiveHundredPxPhotoContainerHandler.this.photoAdapter.addAll(event.getPhotos());
                FiveHundredPxPhotoContainerHandler.this.photoAdapter.notifyDataSetChanged();
            }
        });
    }
}

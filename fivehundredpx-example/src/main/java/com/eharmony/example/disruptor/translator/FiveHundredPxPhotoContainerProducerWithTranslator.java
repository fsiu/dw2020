package com.eharmony.example.disruptor.translator;

import com.eharmony.example.model.FiveHundredPxPhotoContainer;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * Created by fsiu on 4/22/14.
 */
public class FiveHundredPxPhotoContainerProducerWithTranslator {
    private final RingBuffer<FiveHundredPxPhotoContainer> ringBuffer;

    public FiveHundredPxPhotoContainerProducerWithTranslator(final RingBuffer<FiveHundredPxPhotoContainer> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<FiveHundredPxPhotoContainer, FiveHundredPxPhotoContainer> TRANSLATOR =
            new EventTranslatorOneArg<FiveHundredPxPhotoContainer, FiveHundredPxPhotoContainer>() {
                @Override
                public void translateTo(FiveHundredPxPhotoContainer fiveHundredPxPhotoContainer, long l, FiveHundredPxPhotoContainer fiveHundredPxPhotoContainer2) {
                    fiveHundredPxPhotoContainer.setCurrentPage(fiveHundredPxPhotoContainer2.getCurrentPage());
                    fiveHundredPxPhotoContainer.setPhotos(fiveHundredPxPhotoContainer2.getPhotos());
                    fiveHundredPxPhotoContainer.setTotalPages(fiveHundredPxPhotoContainer2.getTotalPages());
                }
            };

    public void onData(final FiveHundredPxPhotoContainer fiveHundredPxPhotoContainer) {
        ringBuffer.publishEvent(TRANSLATOR, fiveHundredPxPhotoContainer);
    }
}

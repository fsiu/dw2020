package net.darkwire.example.disruptor.factory;

import net.darkwire.example.model.FiveHundredPxPhotoContainer;
import com.lmax.disruptor.EventFactory;

/**
 * Created by fsiu on 4/22/14.
 */
public class FiveHundredPxPhotoContainerFactory implements EventFactory<FiveHundredPxPhotoContainer> {
    public FiveHundredPxPhotoContainer newInstance() {
        return new FiveHundredPxPhotoContainer();
    }
}

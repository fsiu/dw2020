package net.darkwire.example;

import java.util.HashMap;

import android.app.Activity;

import com.octo.android.robospice.SpiceManager;

/**
 * Created by fsiu on 3/21/14.
 */
public abstract class BaseSpiceActivity extends BaseActivity {

    private HashMap<String, SpiceManager> spiceManagerMap = new HashMap<String, SpiceManager>();

    @Override
    protected void onStart() {
        for(SpiceManager manager : spiceManagerMap.values()) {
            if(!manager.isStarted()) {
                manager.start(this);
            }
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        for(SpiceManager manager : spiceManagerMap.values()) {
            if(manager.isStarted()) {
                manager.shouldStop();
            }
        }
        super.onStop();
    }

    protected HashMap<String, SpiceManager> getSpiceManagerMap() {
        return spiceManagerMap;
    }

    protected void setSpiceManagerMap(HashMap<String, SpiceManager> spiceManagerMap) {
        this.spiceManagerMap = spiceManagerMap;
    }

    protected void addToSpiceManager(final String name, final SpiceManager spiceManager) {
        this.spiceManagerMap.put(name, spiceManager);
    }

    protected SpiceManager getSpiceManager(final String name) {
        return this.spiceManagerMap.get(name);
    }

}

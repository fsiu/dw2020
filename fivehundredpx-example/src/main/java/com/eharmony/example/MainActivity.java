package com.eharmony.example;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eharmony.example.model.FiveHundredPxPhoto;
import com.fivehundredpx.api.FiveHundredException;
import com.fivehundredpx.api.auth.AccessToken;
import com.fivehundredpx.api.tasks.XAuth500pxTask;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.persistence.exception.SpiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.eharmony.example.model.FiveHundredPxConfiguration;
import com.eharmony.example.widgets.adapter.PhotoAdapter;
import com.eharmony.example.widgets.listener.InfiniteScrollListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.akerfeldt.signpost.retrofit.RetrofitHttpOAuthConsumer;

import com.eharmony.example.model.FiveHundredPxPhotoContainer;
import com.eharmony.example.service.FiveHundredPxSpiceRequest;
import com.eharmony.example.service.FiveHundredPxSpiceService;
import com.eharmony.example.service.client.FiveHundredPxClient;

public class MainActivity extends BaseSpiceActivity  {

    private final Logger LOGGER = LoggerFactory.getLogger(MainActivity.class);

    ArrayAdapter<FiveHundredPxPhoto> listAdapter;

    private int page = 1;
    private int resultsPerPage = 75;
    private int maxPages;

    @InjectView(R.id.list)ListView listView;
    @InjectView(R.id.progress)ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        this.listAdapter = new PhotoAdapter(this, new ArrayList<FiveHundredPxPhoto>());

        final SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(this.listAdapter);
        swingBottomInAnimationAdapter.setInitialDelayMillis(300);
        swingBottomInAnimationAdapter.setAbsListView(this.listView);
        this.listView.setAdapter(swingBottomInAnimationAdapter);

        initialize500pxProperties();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        XAuth500pxTask loginTask = new XAuth500pxTask(new XAuth500pxTaskDelegate());
        final FiveHundredPxConfiguration fiveHundredPxConfiguration = FiveHundredPxConfiguration.INSTANCE;

        loginTask.execute(fiveHundredPxConfiguration.getConsumerKey(),
                fiveHundredPxConfiguration.getConsumerSecret(),
                fiveHundredPxConfiguration.getUsername(),
                fiveHundredPxConfiguration.getPassword());
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    private void initialize500pxProperties() {
        final Properties props = new Properties();
        final InputStream inputStream = ((Object) this).getClass().getClassLoader().getResourceAsStream("secrets.properties");
        try {
            props.load(inputStream);
            final FiveHundredPxConfiguration fiveHundredPxConfiguration = FiveHundredPxConfiguration.INSTANCE;
            fiveHundredPxConfiguration.setConsumerKey(props.getProperty("px_consumer_key"));
            fiveHundredPxConfiguration.setConsumerSecret(props.getProperty("px_consumer_secret"));
            fiveHundredPxConfiguration.setUsername(props.getProperty("username"));
            fiveHundredPxConfiguration.setPassword(props.getProperty("password"));
        }
        catch (Exception e) {

        }
    }

    private class XAuth500pxTaskDelegate implements XAuth500pxTask.Delegate {

        @Override
        public void onSuccess(AccessToken result) {

            final FiveHundredPxConfiguration fiveHundredPxConfiguration = FiveHundredPxConfiguration.INSTANCE;
            final RetrofitHttpOAuthConsumer oAuthConsumer = new RetrofitHttpOAuthConsumer(fiveHundredPxConfiguration.getConsumerKey(), fiveHundredPxConfiguration.getConsumerSecret());
            oAuthConsumer.setTokenWithSecret(result.getToken(), result.getTokenSecret());
            FiveHundredPxClient.INSTANCE.setConsumer(oAuthConsumer);
            addToSpiceManager(MainActivity.class.getName(), new SpiceManager(FiveHundredPxSpiceService.class));

            //normally super.onStart() is managed from this.onStart(), but we have a unique case where we need to get the oAuthConsumer, which should be cached after this
            MainActivity.super.onStart();
            MainActivity.this.listView.setOnScrollListener(new InfiniteScrollListener(resultsPerPage/2) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    loadMoreData(false);
                }
            });
            loadMoreData(true);
        }

        @Override
        public void onFail(FiveHundredException e) {
            ;
        }
    }

    public void loadMoreData(final boolean isInitialLoad) {
        if(isInitialLoad || this.page<this.maxPages) {
            final FiveHundredPxSpiceRequest request = new FiveHundredPxSpiceRequest(this.page, this.resultsPerPage);
            getSpiceManager(MainActivity.class.getName()).execute(request, "test", DurationInMillis.ONE_MINUTE, new FiveHundredPxSpiceRequestListener(isInitialLoad));
        }
    }

    private class FiveHundredPxSpiceRequestListener implements RequestListener<FiveHundredPxPhotoContainer>{

        final boolean isInitialLoad;

        public FiveHundredPxSpiceRequestListener(final boolean isInitialLoad){
            this.isInitialLoad = isInitialLoad;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final FiveHundredPxPhotoContainer result) {
            if(isInitialLoad) {
                MainActivity.this.progressBar.setVisibility(View.GONE);
            }
            MainActivity.this.page++;
            MainActivity.this.maxPages = result.getTotalPages();

            ArrayAdapter<FiveHundredPxPhoto> adapter = MainActivity.this.listAdapter;
            adapter.addAll(result.getPhotos());
            adapter.notifyDataSetChanged();
        }
    }
}

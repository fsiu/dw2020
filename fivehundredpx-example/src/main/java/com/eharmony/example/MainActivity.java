package com.eharmony.example;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
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
    @InjectView(R.id.debug_text)TextView debugTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        final Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> observer) {
                try {
                    observer.onNext(initialize500pxProperties());
                    observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());

        observable.observeOn(Schedulers.io()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                setupListView();
                executeLoginTask();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, getString(R.string.oops), 5000);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private boolean initialize500pxProperties() throws Exception{
        boolean result;
        final Properties props = new Properties();

        final InputStream inputStream = ((Object) this).getClass().getClassLoader().getResourceAsStream(getString(R.string.properties_secrets_file_name));

        try {
            props.load(inputStream);
            final FiveHundredPxConfiguration fiveHundredPxConfiguration = FiveHundredPxConfiguration.INSTANCE;
            fiveHundredPxConfiguration.setConsumerKey(props.getProperty(getString(R.string.properties_secrets_consumer_key_name)));
            fiveHundredPxConfiguration.setConsumerSecret(props.getProperty(getString(R.string.properties_secrets_consumer_secret_name)));
            fiveHundredPxConfiguration.setUsername(props.getProperty(getString(R.string.properties_secrets_username_name)));
            fiveHundredPxConfiguration.setPassword(props.getProperty(getString(R.string.properties_secrets_password_name)));
            result = true;
        }
        catch (Exception e) {
            throw e;
        }
        return result;
    }

    /*
    Arbitrary example where we need to integrate RxJava with an AsyncTask provided by a vendor
     */
    private void executeLoginTask() {
        XAuth500pxTask loginTask = new XAuth500pxTask(new XAuth500pxTaskDelegate());
        final FiveHundredPxConfiguration fiveHundredPxConfiguration = FiveHundredPxConfiguration.INSTANCE;
        loginTask.execute(fiveHundredPxConfiguration.getConsumerKey(),
                fiveHundredPxConfiguration.getConsumerSecret(),
                fiveHundredPxConfiguration.getUsername(),
                fiveHundredPxConfiguration.getPassword());
    }

    private void setupListView() {
        this.listAdapter = new PhotoAdapter(this, new ArrayList<FiveHundredPxPhoto>());
        final SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(this.listAdapter);
        swingBottomInAnimationAdapter.setInitialDelayMillis(300);
        swingBottomInAnimationAdapter.setAbsListView(this.listView);
        this.listView.setAdapter(swingBottomInAnimationAdapter);
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
                MainActivity.this.debugTextView.setVisibility(View.VISIBLE);
            }
            MainActivity.this.page++;
            MainActivity.this.maxPages = result.getTotalPages();

            ArrayAdapter<FiveHundredPxPhoto> adapter = MainActivity.this.listAdapter;
            adapter.addAll(result.getPhotos());
            adapter.notifyDataSetChanged();
        }
    }
}

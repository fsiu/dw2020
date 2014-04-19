package com.eharmony.example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eharmony.example.exception.AuthenticationError;
import com.eharmony.example.tasks.FiveHundredPxAccessToken;
import com.fivehundredpx.api.auth.AccessToken;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.persistence.exception.SpiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eharmony.example.model.FiveHundredPxConfiguration;
import com.eharmony.example.widgets.adapter.PhotoAdapter;
import com.eharmony.example.widgets.listener.InfiniteScrollListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import se.akerfeldt.signpost.retrofit.RetrofitHttpOAuthConsumer;

import com.eharmony.example.model.FiveHundredPxPhotoContainer;
import com.eharmony.example.service.FiveHundredPxSpiceRequest;
import com.eharmony.example.service.FiveHundredPxSpiceService;
import com.eharmony.example.service.client.FiveHundredPxClient;

public class MainActivity extends BaseSpiceActivity  {

    private final Logger LOGGER = LoggerFactory.getLogger(MainActivity.class);

    PhotoAdapter listAdapter;

    @InjectView(R.id.list)ListView listView;
    @InjectView(R.id.progress)ProgressBar progressBar;
    @InjectView(R.id.debug_text)TextView debugTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        startObservers();
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

    private void startObservers() {
        final Observable<BaseAdapter> observable = AndroidObservable.bindActivity(this, Observable.create(new Observable.OnSubscribe<BaseAdapter>() {

            @Override
            public void call(Subscriber<? super BaseAdapter> observer) {
                try {
                    observer.onNext(setupFiveHundredPxAndAdapter());
                    observer.onCompleted();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    observer.onError(e);
                    observer.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()));


        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseAdapter>() {
            @Override
            public void call(final BaseAdapter adapter) {
                setupListView(adapter);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.oops);
                builder.setMessage(throwable.getMessage());
                builder.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startObservers();
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.create().show();
            }
        });
    }

    private BaseAdapter setupFiveHundredPxAndAdapter() throws AuthenticationError {
        final FiveHundredPxConfiguration fiveHundredPxConfiguration = FiveHundredPxConfiguration.INSTANCE;
        final AccessToken accessToken = FiveHundredPxAccessToken.getAccessToken(fiveHundredPxConfiguration);

        final RetrofitHttpOAuthConsumer oAuthConsumer = new RetrofitHttpOAuthConsumer(fiveHundredPxConfiguration.getConsumerKey(), fiveHundredPxConfiguration.getConsumerSecret());
        oAuthConsumer.setTokenWithSecret(accessToken.getToken(), accessToken.getTokenSecret());
        FiveHundredPxClient.INSTANCE.setConsumer(oAuthConsumer);
        addToSpiceManager(MainActivity.class.getName(), new SpiceManager(FiveHundredPxSpiceService.class));

        this.listAdapter = new PhotoAdapter(this);
        this.listAdapter.setResultsPerPage(getResources().getInteger(R.integer.results_per_page));

        final SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(this.listAdapter);
        swingBottomInAnimationAdapter.setInitialDelayMillis(getResources().getInteger(R.integer.transition_delay_duration_in_millis));
        swingBottomInAnimationAdapter.setAbsListView(this.listView);

        return swingBottomInAnimationAdapter;
    }

    private void setupListView(final BaseAdapter adapter) {

        // normally super.onStart() is managed from this.onStart() automatically
        // but we have a unique case where we need to get the oAuthConsumer
        // then add additional spice services
        super.onStart();

        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        this.listView.setOnScrollListener(new InfiniteScrollListener(this.listAdapter.getResultsPerPage()/2) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreData(State.NEXT);
            }
        });
        loadMoreData(State.INITIAL);
    }

    public void loadMoreData(final State loadState) {
        if(State.INITIAL == loadState || this.listAdapter.hasMorePages()) {
            final FiveHundredPxSpiceRequest request = new FiveHundredPxSpiceRequest(this.listAdapter.getCurrentPage(), this.listAdapter.getResultsPerPage());
            getSpiceManager(MainActivity.class.getName()).execute(request, "test", DurationInMillis.ONE_MINUTE, new FiveHundredPxSpiceRequestListener(loadState));
        }
    }

    private class FiveHundredPxSpiceRequestListener implements RequestListener<FiveHundredPxPhotoContainer>{

        final State loadState;

        public FiveHundredPxSpiceRequestListener(final State loadState){
            this.loadState = loadState;
        }

        @Override
        public void onRequestFailure(final SpiceException spiceException) {
            LOGGER.error(spiceException.getMessage());
            Toast.makeText(MainActivity.this, getString(R.string.connection_failed), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(final FiveHundredPxPhotoContainer result) {
            if(State.INITIAL == loadState) {
                MainActivity.this.progressBar.setVisibility(View.GONE);
                MainActivity.this.debugTextView.setVisibility(View.VISIBLE);
            }
            final PhotoAdapter adapter = MainActivity.this.listAdapter;
            adapter.setCurrentPage(adapter.getCurrentPage()+1);
            adapter.setMaxPages(result.getTotalPages());
            adapter.addAll(result.getPhotos());
            adapter.notifyDataSetChanged();
        }
    }

    private enum State {
        INITIAL, NEXT;
    }
}

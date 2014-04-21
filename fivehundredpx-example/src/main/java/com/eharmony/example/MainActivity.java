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
        startObservables();
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

    private Observable<BaseAdapter> getTokenObservable() {
        return AndroidObservable.bindActivity(this, Observable.create(new Observable.OnSubscribe<BaseAdapter>() {
            @Override
            public void call(Subscriber<? super BaseAdapter> subscriber) {
                try {
                    setupWebServices();
                    subscriber.onNext(getNewListViewAdapter());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()));
    }

    private Observable<State> getSetupListViewObservable(final BaseAdapter adapter) {
        return AndroidObservable.bindActivity(this, Observable.create(new Observable.OnSubscribe<State>() {
            @Override
            public void call(final Subscriber<? super State> subscriber) {
                try {
                    subscriber.onNext(addAdapterToListView(adapter));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread()));
    }

    private Action1<State> getLoadMoreDataAction() {
        return new Action1<State>() {
            @Override
            public void call(final State state) {
                loadMoreData(state);
            }
        };
    }

    @Override
    public void startObservables() {
        final Observable<BaseAdapter> observable = getTokenObservable();

        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseAdapter>() {
            @Override
            public void call(final BaseAdapter adapter) {
                final Observable<State> stateObservable = getSetupListViewObservable(adapter);
                stateObservable.observeOn(Schedulers.computation()).subscribe(getLoadMoreDataAction());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.oops)
                        .setMessage(throwable.getMessage())
                        .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startObservables();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).create().show();
            }
        });
    }

    private void setupWebServices() throws AuthenticationError{
        final AccessToken accessToken = FiveHundredPxAccessToken.getAccessToken(FiveHundredPxConfiguration.INSTANCE);
        FiveHundredPxClient.INSTANCE.setConsumer(accessToken);

        final SpiceManager spiceManager = new SpiceManager(FiveHundredPxSpiceService.class);
        addToSpiceManager(MainActivity.class.getName(), spiceManager);
        spiceManager.start(this);
    }

    private void setupListView() {
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        this.listView.setOnScrollListener(new InfiniteScrollListener(this.listAdapter.getResultsPerPage()/2) {

            final Observable observable = Observable.just(State.NEXT).subscribeOn(Schedulers.computation());
            final Action1<State> action = getLoadMoreDataAction();

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                this.observable.subscribeOn(Schedulers.io()).subscribe(this.action);
            }
        });
    }

    private BaseAdapter getNewListViewAdapter() throws AuthenticationError {
        this.listAdapter = new PhotoAdapter(this);
        this.listAdapter.setResultsPerPage(getResources().getInteger(R.integer.results_per_page));

        setupListView();

        final SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(this.listAdapter);
        swingBottomInAnimationAdapter.setInitialDelayMillis(getResources().getInteger(R.integer.transition_delay_duration_in_millis));
        swingBottomInAnimationAdapter.setAbsListView(this.listView);

        return swingBottomInAnimationAdapter;
    }

    private State addAdapterToListView(final BaseAdapter adapter) {
        this.listView.setAdapter(adapter);
        return State.INITIAL;
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
            loadMoreData(loadState);
        }

        @Override
        public void onRequestSuccess(final FiveHundredPxPhotoContainer result) {
            if(State.INITIAL == loadState) {
                MainActivity.this.progressBar.setVisibility(View.GONE);
                MainActivity.this.debugTextView.setVisibility(View.VISIBLE);
            }
            final PhotoAdapter adapter = MainActivity.this.listAdapter;
            adapter.incrementPage();
            adapter.setMaxPages(result.getTotalPages());
            adapter.addAll(result.getPhotos());
            adapter.notifyDataSetChanged();
        }
    }

    private enum State {
        INITIAL, NEXT
    }
}

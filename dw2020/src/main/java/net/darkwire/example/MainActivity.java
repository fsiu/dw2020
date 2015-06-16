package net.darkwire.example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.darkwire.example.exception.AuthenticationError;
import net.darkwire.example.model.FiveHundredPxPhoto;
import net.darkwire.example.service.FiveHundredPxJacksonSpiceService;
import net.darkwire.example.service.FiveHundredPxSearchSpiceRequest;
import net.darkwire.example.service.FiveHundredPxSpiceRequest;
import net.darkwire.example.builder.FiveHundredPxAccessToken;
import com.fivehundredpx.api.auth.AccessToken;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.persistence.exception.SpiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.darkwire.example.model.FiveHundredPxConfiguration;
import net.darkwire.example.widgets.adapter.PhotoAdapter;
import net.darkwire.example.widgets.listener.InfiniteScrollListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.android.app.AppObservable;
import net.darkwire.example.model.FiveHundredPxPhotoContainer;
import net.darkwire.example.service.client.FiveHundredPxClient;

import java.util.ArrayList;

public class MainActivity extends BaseSpiceActivity {

    private static final String CURRENT_PAGE = "currentPage";
    private static final String MAX_PAGES = "maxPages";
    private static final String LIST_ITEMS = "listItems";
    private static final String LIST_VIEW_STATE = "listViewState";
    private static final String ACCESS_TOKEN = "accessToken";
    private final Logger LOGGER = LoggerFactory.getLogger(MainActivity.class);

    private PhotoAdapter listAdapter;

    @InjectView(R.id.list)
    AbsListView listView;
    @InjectView(R.id.progress)
    ProgressBar progressBar;
    @InjectView(R.id.debug_text)
    TextView debugTextView;

    private Bundle bundle;
    private State loadState;
    private FiveHundredPxSpiceRequestListener spiceRequestListener = new FiveHundredPxSpiceRequestListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        this.bundle = savedInstanceState;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startObservables();
    }

    protected void onSaveInstanceState (Bundle outState) {
        if(this.listAdapter!=null && this.listAdapter.getCount()>0) {
            outState.putParcelable(LIST_VIEW_STATE,this.listView.onSaveInstanceState());
            outState.putLong(CURRENT_PAGE, this.listAdapter.getCurrentPage());
            outState.putLong(MAX_PAGES, this.listAdapter.getMaxPages());
            outState.putParcelableArrayList(LIST_ITEMS, this.listAdapter.getList());
        }
        outState.putParcelable(ACCESS_TOKEN, FiveHundredPxClient.INSTANCE.getAccessToken());
        super.onSaveInstanceState(outState);
    }

    private Observable<BaseAdapter> getTokenObservable() {
        return AppObservable.bindActivity(this, Observable.create(new Observable.OnSubscribe<BaseAdapter>() {
            @Override
            public void call(Subscriber<? super BaseAdapter> subscriber) {
                try {
                    setupNetworkServices();
                    subscriber.onNext(getNewListViewAdapter());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    subscriber.onError(e);
                }
            }
        }));
    }

    private Action1<BaseAdapter> getInitialLoadMoreDataAction() {
        return new Action1<BaseAdapter>() {
            @Override
            public void call(final BaseAdapter adapter) {
                final State state = addAdapterToListView(adapter);
                if(adapter.getCount()>0) {
                    MainActivity.this.listView.onRestoreInstanceState(MainActivity.this.bundle.getParcelable(LIST_VIEW_STATE));
                    hideProgressBar();
                } else {
                    loadMoreData(state);
                }
            }
        };
    }

    private Action1<Throwable> getRetryDialogAction(final Runnable successRunnable, final Runnable failureRunnable) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.oops)
                        .setMessage(throwable.getMessage())
                        .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                successRunnable.run();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                failureRunnable.run();
                            }
                        }).create().show();
            }
        };
    }

    @Override
    public void startObservables() {
        getTokenObservable()
                .retry(getResources().getInteger(R.integer.max_retries))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                getInitialLoadMoreDataAction(),
                getRetryDialogAction(
                        new Runnable() {
                            @Override
                            public void run() {
                                startObservables();
                            }
                        }, new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }
                )
        );
    }

    private void setupNetworkServices() throws AuthenticationError {
        AccessToken accessToken = null;
        if(this.bundle!=null) {
            accessToken = this.bundle.getParcelable(ACCESS_TOKEN);
        }
        if(accessToken==null) {
            accessToken = FiveHundredPxAccessToken.build(FiveHundredPxConfiguration.INSTANCE);
        }
        FiveHundredPxClient.INSTANCE.setConsumer(accessToken);

        //final SpiceManager spiceManager = new SpiceManager(FiveHundredPxGsonSpiceService.class);
        final SpiceManager spiceManager = new SpiceManager(FiveHundredPxJacksonSpiceService.class);
        addToSpiceManager(MainActivity.class.getName(), spiceManager);
        spiceManager.start(this);
    }

    private void addListViewListeners(final int scrollerItemThresholdForPreload) {
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        this.listView.setOnScrollListener(new InfiniteScrollListener(scrollerItemThresholdForPreload) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreData(State.NEXT);
            }
        });
    }

    private BaseAdapter getNewListViewAdapter() throws AuthenticationError {
        final int resultsPerPage = getResources().getInteger(R.integer.results_per_page);

        addListViewListeners(resultsPerPage / 2);

        this.listAdapter = new PhotoAdapter(this);
        this.listAdapter.setResultsPerPage(resultsPerPage);

        final SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(this.listAdapter);
        swingBottomInAnimationAdapter.setInitialDelayMillis(getResources().getInteger(R.integer.transition_delay_duration_in_millis));
        swingBottomInAnimationAdapter.setAbsListView(this.listView);

        if(this.bundle!=null) {
            this.listAdapter.setCurrentPage(this.bundle.getLong(CURRENT_PAGE));
            this.listAdapter.setMaxPages(this.bundle.getLong(MAX_PAGES));
            final ArrayList<FiveHundredPxPhoto> photos = this.bundle.getParcelableArrayList(LIST_ITEMS);
            this.listAdapter.addAll(photos);
            swingBottomInAnimationAdapter.setShouldAnimate(false);
        }

        return swingBottomInAnimationAdapter;
    }

    private State addAdapterToListView(final BaseAdapter adapter) {
        ((AdapterView)this.listView).setAdapter(adapter);
        if(adapter.getCount()>0) {
            return State.NEXT;
        } else {
            return State.INITIAL;
        }
    }

    public void loadMoreData(final State loadState) {
        this.loadState = loadState;
        if (State.INITIAL == loadState || this.listAdapter.hasMorePages()) {
            //final FiveHundredPxSpiceRequest request = new FiveHundredPxRecentPhotosSpiceRequest(this.listAdapter.getCurrentPage(), this.listAdapter.getResultsPerPage());
            final FiveHundredPxSpiceRequest request = new FiveHundredPxSearchSpiceRequest(
                    getString(R.string.default_search_term),
                    getString(R.string.default_search_tag),
                    this.listAdapter.getCurrentPage(),
                    this.listAdapter.getResultsPerPage());

            getSpiceManager(MainActivity.class.getName()).execute(request,
                    request.getCacheKey(),
                    DurationInMillis.ONE_HOUR,
                    this.spiceRequestListener);
        }
    }

    private class FiveHundredPxSpiceRequestListener implements RequestListener<FiveHundredPxPhotoContainer> {

        @Override
        public void onRequestFailure(final SpiceException spiceException) {
            LOGGER.error(spiceException.getMessage());
            Toast.makeText(MainActivity.this, getString(R.string.connection_failed), Toast.LENGTH_LONG).show();
            loadMoreData(MainActivity.this.loadState);
        }

        @Override
        public void onRequestSuccess(final FiveHundredPxPhotoContainer result) {
            if (State.INITIAL == MainActivity.this.loadState) {
                hideProgressBar();
            }
            final PhotoAdapter adapter = MainActivity.this.listAdapter;
            adapter.incrementPage();
            adapter.setMaxPages(result.getTotalPages());
            adapter.addAll(result.getPhotos());
            if(MainActivity.this.bundle!=null) {
                final AnimationAdapter animationAdapter = (AnimationAdapter) MainActivity.this.listView.getAdapter();
                animationAdapter.setShouldAnimate(true);
            }
            adapter.notifyDataSetChanged();

        }
    }

    private void hideProgressBar() {
        this.progressBar.setVisibility(View.GONE);
        this.debugTextView.setVisibility(View.VISIBLE);
    }

    private enum State {
        INITIAL, NEXT
    }
}

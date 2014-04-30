package net.darkwire.example.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fsiu on 4/19/14.
 */
public abstract class NumericPaginationBaseAdapter<T> extends BaseAdapter implements NumericPagination {
    private long currentPage = 1;
    private long maxPages;
    private int resultsPerPage;

    final Context ctx;
    final LayoutInflater inflater;
    final ArrayList<T> list = new ArrayList<T>();

    public NumericPaginationBaseAdapter(Context ctx) {
        super();
        this.ctx = ctx;
        this.inflater = (LayoutInflater) this.ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(final int i) {
        return 0;
    }

    @Override
    public long getMaxPages() {
        return this.maxPages;
    }

    @Override
    public void setMaxPages(long maxPages) {
        this.maxPages = maxPages;
    }

    @Override
    public long getCurrentPage() {
        return this.currentPage;
    }

    @Override
    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public int getResultsPerPage() {
        return this.resultsPerPage;
    }

    @Override
    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    @Override
    public void incrementPage() {
        this.currentPage++;
    }

    @Override
    public void decrementPage() {
        this.currentPage--;
    }

    public boolean hasMorePages () {
        return currentPage<maxPages;
    }

    protected void setViewText(final TextView textView, final String text) {
        final boolean empty = Strings.isNullOrEmpty(text);
        final int visibility = empty? View.GONE:View.VISIBLE;
        if(textView.getVisibility()!=visibility) {
            textView.setVisibility(visibility);
        }
        textView.setText(text);
    }

    public int getCount () {
        return this.list.size();
    }

    public T getItem(final int position) {
        return this.list.get(position);
    }

    public void addAll(final List<T> addListItems) {
        this.list.addAll(addListItems);
    }

    public ArrayList<T> getList() {
        return new ArrayList<T>(this.list);
    }

}

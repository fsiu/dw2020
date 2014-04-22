package com.eharmony.example.widgets.adapter;

import android.app.Instrumentation;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.eharmony.example.R;
import com.eharmony.example.model.FiveHundredPxPhoto;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by fsiu on 3/21/14.
 */
public class PhotoAdapter extends NumericPaginationArrayAdapter<FiveHundredPxPhoto> {

    public PhotoAdapter(final Context ctx) {
        super(ctx);
    }

    @Override
    public View getView(final int i, View convertView, final ViewGroup viewGroup) {

        return convertView;
    }

    static final class PhotoViewHolder {
        @InjectView(R.id.image)ImageView imageView;
        @InjectView(R.id.text)TextView textView;

        public PhotoViewHolder(final View view) {
            ButterKnife.inject(this, view);
        }
    }
}

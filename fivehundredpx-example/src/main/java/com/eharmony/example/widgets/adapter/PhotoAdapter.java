package com.eharmony.example.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.eharmony.example.R;
import com.eharmony.example.model.FiveHundredPxPhoto;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by fsiu on 3/21/14.
 */
public class PhotoAdapter extends ArrayAdapter<FiveHundredPxPhoto>{

    final Context ctx;
    final LayoutInflater inflater;

    public PhotoAdapter(final Context ctx, final ArrayList<FiveHundredPxPhoto> items)
    {
        super(ctx, 0);
        this.ctx = ctx;
        this.inflater = (LayoutInflater) this.ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(final int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, final ViewGroup viewGroup) {
        PhotoViewHolder viewHolder;
        if(convertView==null){
            convertView = this.inflater.inflate(R.layout.item, null);
            viewHolder = new PhotoViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (PhotoViewHolder)convertView.getTag();
        }

        final FiveHundredPxPhoto photo = getItem(i);
        Picasso.with(this.ctx)
                .load(photo.getImageUrl())
                .into(viewHolder.imageView);
        setViewText(viewHolder.textView, photo.getName());
        setViewText(viewHolder.secondaryTextView, photo.getShutterSpeed());
        return convertView;
    }

    private void setViewText(final TextView textView, final String text) {
        final boolean empty = Strings.isNullOrEmpty(text);
        final int visibility = empty?View.GONE:View.VISIBLE;
        if(textView.getVisibility()!=visibility) {
            textView.setVisibility(visibility);
        }
        textView.setText(text);
    }

    static final class PhotoViewHolder {
        @InjectView(R.id.image)ImageView imageView;
        @InjectView(R.id.text)TextView textView;
        @InjectView(R.id.secondary_text) TextView secondaryTextView;

        public PhotoViewHolder(final View view) {
            ButterKnife.inject(this, view);
        }
    }
}

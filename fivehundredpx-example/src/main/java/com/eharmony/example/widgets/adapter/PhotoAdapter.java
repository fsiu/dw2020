package com.eharmony.example.widgets.adapter;

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
        PhotoViewHolder viewHolder;
        if(convertView==null){
            convertView = this.inflater.inflate(R.layout.item, viewGroup, false);
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

    static final class PhotoViewHolder {
        @InjectView(R.id.image)ImageView imageView;
        @InjectView(R.id.text)TextView textView;
        @InjectView(R.id.secondary_text) TextView secondaryTextView;

        public PhotoViewHolder(final View view) {
            ButterKnife.inject(this, view);
        }
    }
}

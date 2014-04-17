package com.eharmony.example.widgets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.eharmony.example.R;
import com.eharmony.example.model.FiveHundredPxPhoto;

import com.nhaarman.listviewanimations.ArrayAdapter;

/**
 * Created by fsiu on 3/21/14.
 */
public class PhotoAdapter extends ArrayAdapter<FiveHundredPxPhoto>{

    final Context ctx;
    final LayoutInflater inflater;

    public PhotoAdapter(final Context ctx, final ArrayList<FiveHundredPxPhoto> items)
    {
        super(items);
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
            viewHolder = new PhotoViewHolder();
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.text);
            viewHolder.secondaryTextView = (TextView)convertView.findViewById(R.id.secondary_text);
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

    private static final class PhotoViewHolder {
        ImageView imageView;
        TextView textView;
        TextView secondaryTextView;
    }
}

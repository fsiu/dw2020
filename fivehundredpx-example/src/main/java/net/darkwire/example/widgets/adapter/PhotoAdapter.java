package net.darkwire.example.widgets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import net.darkwire.example.R;
import net.darkwire.example.model.FiveHundredPxPhoto;

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
    public View getView(final int position, View convertView, final ViewGroup viewGroup) {
        PhotoViewHolder viewHolder;
        if(convertView==null){
            convertView = this.inflater.inflate(R.layout.item, viewGroup, false);
            viewHolder = new PhotoViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (PhotoViewHolder)convertView.getTag();
        }

        final FiveHundredPxPhoto photo = getItem(position);
        Picasso.with(this.ctx)
                .load(photo.getImageUrl())
                .into(viewHolder.imageView);
        setViewText(viewHolder.textView, photo.getName());
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

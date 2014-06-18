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
public class PhotoAdapter extends NumericPaginationBaseAdapter<FiveHundredPxPhoto> {

    public PhotoAdapter(final Context ctx) {
        super(ctx);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup viewGroup) {
        //View layout = R.layout.item
        //FiveHundredPxPhoto.getImageUrl() for the photo url
        //FiveHundredPxPhoto.getName() to setTextView() from the NumericPaginationBaseAdapter
        //Use Picasso to populate the image
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

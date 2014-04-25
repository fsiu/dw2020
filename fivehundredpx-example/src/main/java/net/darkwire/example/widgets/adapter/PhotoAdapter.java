package net.darkwire.example.widgets.adapter;

import android.content.Context;
<<<<<<< HEAD:fivehundredpx-example/src/main/java/com/eharmony/example/widgets/adapter/PhotoAdapter.java
=======
import android.content.res.Resources;
>>>>>>> refactor-initial-import:fivehundredpx-example/src/main/java/net/darkwire/example/widgets/adapter/PhotoAdapter.java
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

<<<<<<< HEAD:fivehundredpx-example/src/main/java/com/eharmony/example/widgets/adapter/PhotoAdapter.java
import com.eharmony.example.R;
import com.eharmony.example.model.FiveHundredPxPhoto;
=======
import net.darkwire.example.R;
import net.darkwire.example.model.FiveHundredPxPhoto;
>>>>>>> refactor-initial-import:fivehundredpx-example/src/main/java/net/darkwire/example/widgets/adapter/PhotoAdapter.java

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
<<<<<<< HEAD:fivehundredpx-example/src/main/java/com/eharmony/example/widgets/adapter/PhotoAdapter.java

=======
>>>>>>> refactor-initial-import:fivehundredpx-example/src/main/java/net/darkwire/example/widgets/adapter/PhotoAdapter.java
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

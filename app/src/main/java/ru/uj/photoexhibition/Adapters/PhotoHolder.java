package ru.uj.photoexhibition.Adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import ru.uj.photoexhibition.DataClasses.Photo;
import ru.uj.photoexhibition.R;

/**
 * Created by Blokhin Evgeny on 12.11.2018.
 */
public class PhotoHolder extends RecyclerView.ViewHolder {

    protected ImageView mItemImageView;
    private Photo mPhoto;

    public PhotoHolder(@NonNull View itemView) {
        super(itemView);

        mItemImageView = itemView.findViewById(R.id.item_image_view);
    }

    public void bindDrawable(Drawable drawable) {
        mItemImageView.setImageDrawable(drawable);
    }

    public void bindGalleryItem(Photo galleryItem) {
        mPhoto = galleryItem;
    }

}

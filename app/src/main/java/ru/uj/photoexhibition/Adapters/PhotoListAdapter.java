package ru.uj.photoexhibition.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import io.realm.RealmChangeListener;
import ru.uj.photoexhibition.DataClasses.Photo;
import ru.uj.photoexhibition.DataClasses.PhotoLab;
import ru.uj.photoexhibition.PhotoList.IPhotoListPresenter;
import ru.uj.photoexhibition.R;

/**
 * Created by Blokhin Evgeny on 12.11.2018.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoHolder> implements RealmChangeListener {


    private List<Photo> mPhotoListItems;
    private Context mContext;
    private IPhotoListPresenter mPresenter;

    public PhotoListAdapter(List<Photo> photoListItems, Context context, IPhotoListPresenter presenter) {
        mPhotoListItems = photoListItems;
        mContext = context;
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_item, viewGroup,false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder photoHolder, int position) {
        Photo photo = mPhotoListItems.get(position);
        photoHolder.bindGalleryItem(photo);
        File photoFile = PhotoLab.get(mContext).getPhotoFile(photo);
        Picasso.with(mContext).load(photoFile).fit().into(photoHolder.mItemImageView);
    }

    @Override
    public int getItemCount() {
        return mPhotoListItems.size();
    }

    public void setPhotoListItems(List<Photo> photoListItems) {
        mPhotoListItems = photoListItems;
        notifyDataSetChanged();
    }

    @Override
    public void onChange(Object o) {
        notifyDataSetChanged();
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}

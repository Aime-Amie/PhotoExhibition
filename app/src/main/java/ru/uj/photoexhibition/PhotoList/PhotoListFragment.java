package ru.uj.photoexhibition.PhotoList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Objects;

import ru.uj.photoexhibition.Adapters.PhotoListAdapter;
import ru.uj.photoexhibition.DataClasses.Photo;
import ru.uj.photoexhibition.DataClasses.PhotoLab;
import ru.uj.photoexhibition.R;
import ru.uj.photoexhibition.SlideShowDialog.SlideShowDialogFragment;

/**
 * Created by Blokhin Evgeny on 09.11.2018.
 */
public class PhotoListFragment extends Fragment implements IPhotoListView {

    private ProgressDialog mProgress;
    private RecyclerView mRecyclerView;
    private PhotoListAdapter mListAdapter;
    private LinearLayout mLayoutIsEmpty;
    private List<Photo> mPhotos;
    private Button mButtonAddIsEmpty;
    private Button mButtonAdd;
    private IPhotoListPresenter mPhotoListPresenter;

    public static PhotoListFragment newInstance() {
        return new PhotoListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mPhotoListPresenter = new PhotoListPresenter();
        }

        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_list, container, false);
        mLayoutIsEmpty = v.findViewById(R.id.empty_layout);
        mRecyclerView = v.findViewById(R.id.photo_recycler_view);
        mButtonAdd = v.findViewById(R.id.new_image);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        final ViewTreeObserver observer = mRecyclerView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(() -> {
            RecyclerView.LayoutManager lm = mRecyclerView.getLayoutManager();
            int totalSpace;
            if (((LinearLayoutManager) lm).getOrientation() == LinearLayoutManager.VERTICAL) {
                totalSpace = lm.getWidth() - lm.getPaddingRight() - lm.getPaddingLeft();
            } else {
                totalSpace = lm.getHeight() - lm.getPaddingTop() - lm.getPaddingBottom();
            }
            int spanCount = Math.max(1, totalSpace / 220);
            ((GridLayoutManager) mRecyclerView.getLayoutManager()).setSpanCount(spanCount);
        });

        mRecyclerView.addOnItemTouchListener(new PhotoListAdapter.RecyclerTouchListener(getContext(), mRecyclerView, new PhotoListAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                SlideShowDialogFragment newFragment = SlideShowDialogFragment.newInstance(position);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        mButtonAddIsEmpty = v.findViewById(R.id.button_empty_list);
        mButtonAddIsEmpty.setOnClickListener(view -> {
            mPhotoListPresenter.openPhotoAddImage();
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_photo_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_image:
                mPhotoListPresenter.openPhotoAddImage();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateUI() {
        PhotoLab photoLab = PhotoLab.get(getActivity());
        mPhotos = photoLab.getPhotos();
        if (mPhotos.isEmpty()) {
            showEmptyView();
        } else {
            hideEmptyView();
        }
        if (mListAdapter == null) {
            mListAdapter = new PhotoListAdapter(mPhotos, getActivity(), mPhotoListPresenter);
            mRecyclerView.setAdapter(mListAdapter);
        } else {
            mListAdapter.setPhotoListItems(mPhotos);
        }
    }


    @Override
    public void showEmptyView() {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutIsEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mLayoutIsEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPhotoListPresenter.bindView(this);
        updateUI();
    }

    @Override
    public void onPause() {
        mPhotoListPresenter.unbindView();
        super.onPause();
    }
}

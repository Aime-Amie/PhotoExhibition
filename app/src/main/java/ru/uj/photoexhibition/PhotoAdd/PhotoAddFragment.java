package ru.uj.photoexhibition.PhotoAdd;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import ru.uj.photoexhibition.DataClasses.Photo;
import ru.uj.photoexhibition.DataClasses.PhotoLab;
import ru.uj.photoexhibition.R;

/**
 * Created by Blokhin Evgeny on 12.11.2018.
 */
public class PhotoAddFragment extends Fragment implements IPhotoAddView {

    private Photo mPhoto;
    private File mPhotoFile;
    private ImageView mImageView;
    private ImageButton mAddSaveImageBatton;
    private ImageButton mCancelImageBatton;
    private static final String ARG_PHOTO_ID = "crime_id";
    private static final int REQUEST_PHOTO = 1;
    private IPhotoAddPresenter mPhotoAddPresenter;


    public static PhotoAddFragment newInstance() {
        return new PhotoAddFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState == null) {
            mPhotoAddPresenter = new PhotoAddPresenter();
        }
//        else {
//            mPhotoAddPresenter = PresenterHolder.getInstance().restorePresenter(savedInstanceState);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_add_picture, container, false);

        mImageView = v.findViewById(R.id.image_preview);
        mAddSaveImageBatton = v.findViewById(R.id.add_save_image_button);
        mCancelImageBatton = v.findViewById(R.id.cancel_image_button);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        PackageManager packageManager = getActivity().getPackageManager();
        boolean canTakePhoto = captureImage.resolveActivity(packageManager) != null;
        mAddSaveImageBatton.setEnabled(canTakePhoto);


        mAddSaveImageBatton.setOnClickListener(v1 -> {
            if (mPhotoFile == null) {
                mPhoto = PhotoLab.get(getActivity()).addPhoto();
                mPhotoFile = PhotoLab.get(getActivity()).getPhotoFile(mPhoto);
                Uri uri = FileProvider.getUriForFile(getActivity(), "ru.uj.android.photoexhibition.fileprovider", mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            } else {
                getActivity().finish();
            }

        });

        mCancelImageBatton.setOnClickListener(v1 -> {
            if (mPhotoFile == null) {
                getActivity().finish();
            } else {
                PhotoLab.get(getActivity()).deletePhoto(mPhoto);
                getActivity().finish();
            }
        });
        updatePhotoView();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(), "ru.uj.android.photoexhibition.fileprovider", mPhotoFile);
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mAddSaveImageBatton.setBackgroundResource(R.drawable.button_add);
            mImageView.setImageDrawable(null);
        } else {
            mAddSaveImageBatton.setBackgroundResource(R.drawable.button_save);
            Picasso.with(getActivity()).load(mPhotoFile).fit().into(mImageView);
        }
    }

    @Override

    public void onDestroy() {

        super.onDestroy();


    }

    @Override
    public void onResume() {
        super.onResume();
        mPhotoAddPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        mPhotoAddPresenter.unbindView();
        super.onPause();
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        PresenterHolder.getInstance().savePresenter((BasePresenter<?>) mPhotoAddPresenter, outState);
//    }
}

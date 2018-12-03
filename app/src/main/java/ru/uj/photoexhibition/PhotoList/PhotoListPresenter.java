package ru.uj.photoexhibition.PhotoList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

import ru.uj.photoexhibition.BasePresenter;
import ru.uj.photoexhibition.DataClasses.Photo;
import ru.uj.photoexhibition.PhotoAdd.PhotoAddActivity;

/**
 * Created by Blokhin Evgeny on 17.11.2018.
 */
public class PhotoListPresenter extends BasePresenter<IPhotoListView> implements IPhotoListPresenter {

    private List<Photo> loadedPhoto;

    @Override
    public void openPhotoAddImage() {
        Context ctx = ((Fragment)view).getActivity();
        Intent intent = new Intent(ctx, PhotoAddActivity.class);
        ctx.startActivity(intent);
    }

//    @Override
//    public void getPhotoList() {
//        if (!loadedPhoto.isEmpty()){
//            view.onGetPhotos(loadedPhoto);
//            return;
//        }
//        view.showProgressDialog("Getting photos list...");
//    }

    @Override
    public void bindView(IPhotoListView view) {
        super.bindView(view);
    }
}
